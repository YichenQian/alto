#define _GNU_SOURCE
#include <string.h>
#include <math.h>
#include <unistd.h>
#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <cblas.h>
#include <pthread.h>
#include <semaphore.h>
#include <errno.h>

#include "SIMULATION/RF/defs.h"
#include "PHY/types.h"
#include "PHY/defs.h"
#include "PHY/vars.h"
#include "MAC_INTERFACE/vars.h"

//#ifdef OPENAIR2
#include "LAYER2/MAC/defs.h"
#include "LAYER2/MAC/vars.h"
#include "RRC/LITE/vars.h"
#include "PHY_INTERFACE/vars.h"
//#endif

#include "ARCH/CBMIMO1/DEVICE_DRIVER/vars.h"

#ifdef IFFT_FPGA
//#include "PHY/LTE_REFSIG/mod_table.h"
#endif //IFFT_FPGA

#include "SCHED/defs.h"
#include "SCHED/vars.h"
#include "PHY_INTERFACE/shm_mem.h"

#ifdef XFORMS
#include <forms.h>
#include "phy_procedures_sim_form.h"
#include "../../../openair1/USERSPACE_TOOLS/SCOPE/lte_scope.h"
#endif //XFORMS

#include "oaisim.h"
#include "oaisim_config.h"
#include "UTIL/OCG/OCG_extern.h"
#include "cor_SF_sim.h"
#include "UTIL/OMG/omg_constants.h"


//#ifdef PROC
#include "../PROC/interface.h"
#include "../PROC/channel_sim_proc.h"
#include "../PROC/Tsync.h"
#include "../PROC/Process.h"
//#endif

#include "UTIL/LOG/vcd_signal_dumper.h"

#define RF

//#define DEBUG_SIM

#define MCS_COUNT 24//added for PHY abstraction
#define N_TRIALS 1
/*
  DCI0_5MHz_TDD0_t          UL_alloc_pdu;
  DCI1A_5MHz_TDD_1_6_t      CCCH_alloc_pdu;
  DCI2_5MHz_2A_L10PRB_TDD_t DLSCH_alloc_pdu1;
  DCI2_5MHz_2A_M10PRB_TDD_t DLSCH_alloc_pdu2;
 */

#define UL_RB_ALLOC computeRIV(lte_frame_parms->N_RB_UL,0,24)
#define CCCH_RB_ALLOC computeRIV(lte_frame_parms->N_RB_UL,0,3)
#define RA_RB_ALLOC computeRIV(lte_frame_parms->N_RB_UL,0,3)
#define DLSCH_RB_ALLOC 0x1fff

#define DECOR_DIST 100
#define SF_VAR 10

//constant for OAISIM soft realtime calibration
#define SF_DEVIATION_OFFSET_NS 100000 //= 0.1ms : should be as a number of UE
#define SLEEP_STEP_US       100	//  = 0.01ms could be adaptive, should be as a number of UE
#define K 2                  // averaging coefficient 
#define TARGET_SF_TIME_NS 1000000	// 1ms = 1000000 ns

#define IP_address "192.168.1.105"
#define BUFFER_SIZE 1024

//#ifdef OPENAIR2
//u16 NODE_ID[1];
//u8 NB_INST = 2;
//#endif //OPENAIR2

//make change 
int define_flag = 0;
int ack_flag = 0;
pthread_mutex_t mutex;
pthread_mutex_t t_mutex;
char* socket_recieve_buffer;
char* socket_pdcp_buffer;
char ack_buffer[2];
int total_length;
int client_socket;
sem_t ACKOK;

struct buffer_for_send {
    int length;
    int tpye;
    char buffer[BUFFER_SIZE];
} *buff_p;

int mac_phy_buf_size = 0;
struct msg_st data;
int semid;
int msgid = -1;
int shmid = -1;
long int msgtype = 0;
char * shmaddr;
unsigned int c_times = 0;
////////////////////////////////////

char stats_buffer[16384];
channel_desc_t *eNB2UE[NUMBER_OF_eNB_MAX][NUMBER_OF_UE_MAX];
channel_desc_t *UE2eNB[NUMBER_OF_UE_MAX][NUMBER_OF_eNB_MAX];
//Added for PHY abstraction
node_desc_t *enb_data[NUMBER_OF_eNB_MAX];
node_desc_t *ue_data[NUMBER_OF_UE_MAX];
double sinr_bler_map[MCS_COUNT][2][16];

extern void kpi_gen();

// this should reflect the channel models in openair1/SIMULATION/TOOLS/defs.h
mapping small_scale_names[] = {
    {"custom", 0},
    {"SCM_A", 1},
    {"SCM_B", 2},
    {"SCM_C", 3},
    {"SCM_D", 4},
    {"EPA", 5},
    {"EVA", 6},
    {"ETU", 7},
    {"Rayleigh8", 8},
    {"Rayleigh1", 9},
    {"Rayleigh1_corr", 10},
    {"Rayleigh1_anticorr", 11},
    {"Rice8", 12},
    {"Rice1", 13},
    {"Rice1_corr", 14},
    {"Rice1_anticorr", 15},
    {"AWGN", 16},
    {NULL, -1}
};

static void *sigh(void *arg);
void terminate(void);
void exit_fun(const char* s);

void
help(void) {
    printf
            ("Usage: oaisim -h -a -F -C tdd_config -V -R N_RB_DL -e -x transmission_mode -m target_dl_mcs -r(ate_adaptation) -n n_frames -s snr_dB -k ricean_factor -t max_delay -f forgetting factor -A channel_model -z cooperation_flag -u nb_local_ue -U UE mobility -b nb_local_enb -B eNB_mobility -M ethernet_flag -p nb_master -g multicast_group -l log_level -c ocg_enable -T traffic model\n");

    printf("-h provides this help message!\n");
    printf("-a Activates PHY abstraction mode\n");
    printf("-F Activates FDD transmission (TDD is default)\n");
    printf("-C [0-6] Sets TDD configuration\n");
    printf("-R [6,15,25,50,75,100] Sets N_RB_DL\n");
    printf("-e Activates extended prefix mode\n");
    printf("-m Gives a fixed DL mcs\n");
    printf("-r Activates rate adaptation (DL for now)\n");
    printf("-n Set the number of frames for the simulation\n");
    printf("-s snr_dB set a fixed (average) SNR, this deactivates the openair channel model generator (OCM)\n");
    printf("-S snir_dB set a fixed (average) SNIR, this deactivates the openair channel model generator (OCM)\n");
    printf("-k Set the Ricean factor (linear)\n");
    printf("-t Set the delay spread (microseconds)\n");
    printf("-f Set the forgetting factor for time-variation\n");
    printf("-A set the multipath channel simulation,  options are: SCM_A, SCM_B, SCM_C, SCM_D, EPA, EVA, ETU, Rayleigh8, Rayleigh1, Rayleigh1_corr,Rayleigh1_anticorr, Rice8,, Rice1, AWGN \n");
    printf("-b Set the number of local eNB\n");
    printf("-u Set the number of local UE\n");
    printf("-M Set the machine ID for Ethernet-based emulation\n");
    printf("-p Set the total number of machine in emulation - valid if M is set\n");
    printf("-g Set multicast group ID (0,1,2,3) - valid if M is set\n");
    printf("-l Set the global log level (8:trace, 7:debug, 6:info, 4:warn, 3:error) \n");
    printf
            ("-c [1,2,3,4] Activate the config generator (OCG) to process the scenario descriptor, or give the scenario manually: -c template_1.xml \n");
    printf("-x Set the transmission mode (1,2,5,6 supported for now)\n");
    printf("-z Set the cooperation flag (0 for no cooperation, 1 for delay diversity and 2 for distributed alamouti\n");
    printf("-T activate the traffic generator: 0 for NONE, 1 for CBR, 2 for M2M, 3 for FPS Gaming, 4 for mix\n");
    printf("-B Set the mobility model for eNB, options are: STATIC, RWP, RWALK, \n");
    printf("-U Set the mobility model for UE, options are: STATIC, RWP, RWALK \n");
    printf("-E Random number generator seed\n");
    printf("-P enable protocol analyzer : 0 for wireshark interface, 1: for pcap , 2 : for tshark \n");
    printf("-I Enable CLI interface (to connect use telnet localhost 1352)\n");
    printf("-V Enable VCD dump, file = openair_vcd_dump.vcd\n");
    printf("-G Enable background traffic \n");
    printf("-O [mme ipv4 address] Enable MME mode\n");
    printf("-Z Reserved\n");
}

int omv_write(int pfd, Node_list enb_node_list, Node_list ue_node_list, Data_Flow_Unit omv_data) {
    int i, j;
    omv_data.end = 0;
    //omv_data.total_num_nodes = NB_UE_INST + NB_eNB_INST;
    for (i = 0; i < NB_eNB_INST; i++) {
        if (enb_node_list != NULL) {
            omv_data.geo[i].x = (enb_node_list->node->X_pos < 0.0) ? 0.0 : enb_node_list->node->X_pos;
            omv_data.geo[i].y = (enb_node_list->node->Y_pos < 0.0) ? 0.0 : enb_node_list->node->Y_pos;
            omv_data.geo[i].z = 1.0;
            omv_data.geo[i].mobility_type = oai_emulation.info.omg_model_enb;
            omv_data.geo[i].node_type = 0; //eNB
            enb_node_list = enb_node_list->next;
            omv_data.geo[i].Neighbors = 0;
            for (j = NB_eNB_INST; j < NB_UE_INST + NB_eNB_INST; j++) {
                if (is_UE_active(i, j - NB_eNB_INST) == 1) {
                    omv_data.geo[i].Neighbor[omv_data.geo[i].Neighbors] = j;
                    omv_data.geo[i].Neighbors++;
                    LOG_D(OMG, "[eNB %d][UE %d] is_UE_active(i,j) %d geo (x%d, y%d) num neighbors %d\n", i, j - NB_eNB_INST, is_UE_active(i, j - NB_eNB_INST),
                            omv_data.geo[i].x, omv_data.geo[i].y, omv_data.geo[i].Neighbors);
                }
            }
        }
    }
    for (i = NB_eNB_INST; i < NB_UE_INST + NB_eNB_INST; i++) {
        if (ue_node_list != NULL) {
            omv_data.geo[i].x = (ue_node_list->node->X_pos < 0.0) ? 0.0 : ue_node_list->node->X_pos;
            omv_data.geo[i].y = (ue_node_list->node->Y_pos < 0.0) ? 0.0 : ue_node_list->node->Y_pos;
            omv_data.geo[i].z = 1.0;
            omv_data.geo[i].mobility_type = oai_emulation.info.omg_model_ue;
            omv_data.geo[i].node_type = 1; //UE
            //trial
            omv_data.geo[i].state = 1;
            omv_data.geo[i].rnti = 88;
            omv_data.geo[i].connected_eNB = 0;
            omv_data.geo[i].RSRP = 66;
            omv_data.geo[i].RSRQ = 55;
            omv_data.geo[i].Pathloss = 44;
            omv_data.geo[i].RSSI[0] = 33;
            omv_data.geo[i].RSSI[1] = 22;
            omv_data.geo[i].RSSI[2] = 11;

            ue_node_list = ue_node_list->next;
            omv_data.geo[i].Neighbors = 0;
            for (j = 0; j < NB_eNB_INST; j++) {
                if (is_UE_active(j, i - NB_eNB_INST) == 1) {
                    omv_data.geo[i].Neighbor[ omv_data.geo[i].Neighbors] = j;
                    omv_data.geo[i].Neighbors++;
                    LOG_D(OMG, "[UE %d][eNB %d] is_UE_active  %d geo (x%d, y%d) num neighbors %d\n", i - NB_eNB_INST, j, is_UE_active(j, i - NB_eNB_INST),
                            omv_data.geo[i].x, omv_data.geo[i].y, omv_data.geo[i].Neighbors);
                }
            }
        }
    }

    if (write(pfd, &omv_data, sizeof (struct Data_Flow_Unit)) == -1)
        perror("write omv failed");
    return 1;
}

void omv_end(int pfd, Data_Flow_Unit omv_data) {
    omv_data.end = 1;
    if (write(pfd, &omv_data, sizeof (struct Data_Flow_Unit)) == -1)
        perror("write omv failed");
}

//make change add ip
//设置一个socket地址结构client_addr,代表客户机internet地址, 端口
#define BUFFER_SIZE 1024

void socket_recieve() {
    int length;
    struct sockaddr_in client_addr;
    bzero(&client_addr, sizeof (client_addr)); //把一段内存区的内容全部设置为0
    client_addr.sin_family = AF_INET; //internet协议族
    client_addr.sin_addr.s_addr = htons(INADDR_ANY); //INADDR_ANY表示自动获取本机地址
    client_addr.sin_port = htons(0); //0表示让系统自动分配一个空闲端口
    //创建用于internet的流协议(TCP)socket,用client_socket代表客户机socket
    client_socket = socket(AF_INET, SOCK_STREAM, 0);
    if (client_socket < 0) {
        printf("Create Socket Failed!\n");
        exit(1);
    }
    //把客户机的socket和客户机的socket地址结构联系起来
    if (bind(client_socket, (struct sockaddr*) &client_addr, sizeof (client_addr))) {
        printf("Client Bind Port Failed!\n");
        exit(1);
    }

    //设置一个socket地址结构server_addr,代表服务器的internet地址, 端口
    struct sockaddr_in server_addr;
    bzero(&server_addr, sizeof (server_addr));
    server_addr.sin_family = AF_INET;
    const char * server_ip = IP_address;
    if (inet_aton(server_ip, &server_addr.sin_addr) == 0) //服务器的IP地址来自程序的参数
    {
        printf("Server IP Address Error!\n");
        exit(1);
    }
    server_addr.sin_port = htons(6666);
    socklen_t server_addr_length = sizeof (server_addr);
    //向服务器发起连接,连接成功后client_socket代表了客户机和服务器的一个socket连接
    if (connect(client_socket, (struct sockaddr*) &server_addr, server_addr_length) < 0) {
        printf("Can Not Connect To Server!\n");
        exit(1);
    }

    char file_name[100 + 1];
    bzero(file_name, 100 + 1);

    char buffer[BUFFER_SIZE];
    bzero(buffer, BUFFER_SIZE);

    char *definefile;
    definefile = (char*) malloc(50);

    int np = 0;
    int count = 0;

    //    strncpy(buffer, file_name, strlen(file_name)>BUFFER_SIZE?BUFFER_SIZE:strlen(file_name));
    //向服务器发送buffer中的数据
    //    send(client_socket,buffer,BUFFER_SIZE,0);

    //    int fp = open(file_name, O_WRONLY|O_CREAT);
    //    if( fp < 0 )
    //    FILE * fp = fopen(file_name,"w");
    //    if(NULL == fp )
    //    {
    //        printf("File:\t%s Can Not Open To Write\n", file_name);
    //        exit(1);
    //    }

    //从服务器接收数据到buffer中
    while (1) {
        bzero(buffer, BUFFER_SIZE);
        length = 0;
        while (length = recv(client_socket, buffer, BUFFER_SIZE, 0)) {
            if (length < 0) {
                printf("Recieve Data Failed!\n");
                break;
            }
            //        int write_length = write(fp, buffer,length);

            /*
            FILE *fp;
            sprintf(definefile, "/home/cic/iptest_server/data/test_order%04d.txt", count);
            fp = fopen(definefile,"w");
            fwrite(buffer, 1, length, fp);
            fclose(fp);
             * */
            count++;

            np = 0;
            while (np < length) {
                buff_p = (struct buffer_for_send*) &(buffer[np]);
                if (buff_p->tpye == 2) {
                    memcpy(ack_buffer, buff_p->buffer, buff_p->length - 8);
                    //pthread_mutex_lock(&mutex); // 给互斥体变量加锁
                    //ack_flag = 1;
                    sem_post(&ACKOK);
                    //pthread_mutex_unlock(&mutex); // 给互斥体变量解除锁
                } else {
                    memcpy(&socket_recieve_buffer[total_length], buff_p->buffer, buff_p->length - 8);
                    pthread_mutex_lock(&mutex); // 给互斥体变量加锁 
                    define_flag = 1;
                    total_length += (buff_p->length - 8);
                    pthread_mutex_unlock(&mutex); // 给互斥体变量解除锁
                }
                np += buff_p->length;
            }
            /*
            if(length ==1)
            {
                memcpy(ack_buffer, buffer, length);
                pthread_mutex_lock(&mutex); // 给互斥体变量加锁 
                ack_flag = 1;
                pthread_mutex_unlock(&mutex); // 给互斥体变量解除锁
            }
            else
            {
                memcpy(socket_recieve_buffer, buffer, length);
                pthread_mutex_lock(&mutex); // 给互斥体变量加锁 
                define_flag = 1;
                total_length += length;
                pthread_mutex_unlock(&mutex); // 给互斥体变量解除锁
            }
             */
        }
    }
}
///////////////////

int
main(int argc, char **argv) {
    char c;
    s32 i, j;
    int new_omg_model; // goto ocg in oai_emulation.info.
    // pointers signal buffers (s = transmit, r,r0 = receive)
    double **s_re, **s_im, **r_re, **r_im, **r_re0, **r_im0;
    double forgetting_factor = 0.0;
    int map1, map2;
    double **ShaF = NULL;
    u32 frame = 0;

    // Framing variables
    s32 slot, last_slot, next_slot;

    // variables/flags which are set by user on command-line
    double snr_dB, sinr_dB, snr_direction; //,sinr_direction;
    u8 set_sinr = 0; //,set_snr=0;
    u8 ue_connection_test = 0;
    u8 set_seed = 0;
    u8 cooperation_flag; // for cooperative communication
    u8 target_dl_mcs = 4;
    u8 target_ul_mcs = 2;
    u8 rate_adaptation_flag;

    u8 abstraction_flag = 0, ethernet_flag = 0;

    u16 Nid_cell = 0;
    s32 UE_id, eNB_id, ret;

    // time calibration for soft realtime mode  
    struct timespec time_spec;
    struct timespec start_time, end_time;
    unsigned long time_last, time_now;
    int td, td_avg, sleep_time_us;

    lte_subframe_t direction;

    // omv related info
    //pid_t omv_pid;
    char full_name[200];
    int pfd[2]; // fd for omv : fixme: this could be a local var
    char fdstr[10];
    char frames[10];
    char num_enb[10];
    char num_ue[10];
    //area_x, area_y and area_z for omv
    char x_area[20];
    char y_area[20];
    char z_area[20];
    char fname[64], vname[64];
    char nb_antenna[20];
    char frame_type[10];
    char tdd_config[10];

    LTE_DL_FRAME_PARMS *frame_parms;

    FILE * UE_stats[NUMBER_OF_UE_MAX], *eNB_stats, *eNB_avg_thr;

    char UE_stats_filename[255];

    int len;

    //make change
    pthread_mutex_init(&mutex, NULL);
    pthread_mutex_init(&t_mutex, NULL);
    define_flag = 0;
    ack_flag = 0;
    total_length = 0;
    socket_recieve_buffer = (char*) malloc(1000);
    socket_pdcp_buffer = (char*) malloc(1000);
//    cpu_set_t mask;
//    cpu_set_t get;
//    CPU_ZERO(&mask);
//    CPU_SET(0, &mask);
//    if (sched_setaffinity(0, sizeof (mask), &mask) < 0) {
//        fprintf(stderr, "set thread affinity failed\n");
//    }
    /////////////////

    // Added for PHY abstraction
    Node_list ue_node_list = NULL;
    Node_list enb_node_list = NULL;
    Data_Flow_Unit omv_data;
    //ALU

    int port, node_id = 0, Process_Flag = 0, wgt, Channel_Flag = 0, temp;
    target_dl_mcs = 0;
    rate_adaptation_flag = 0;
    oai_emulation.info.n_frames = 0xffff; //1024;		//100;
    oai_emulation.info.n_frames_flag = 0; //fixme
    snr_dB = 30;
    cooperation_flag = 0; // default value 0 for no cooperation, 1 for Delay diversity, 2 for Distributed Alamouti


    logInit();

    init_oai_emulation(); // to initialize everything !!!



    // configure oaisim with OCG
    oaisim_config(); // config OMG and OCG, OPT, OTG, OLG
    // Initialize VCD LOG module
    vcd_signal_dumper_init();


    // fix ethernet and abstraction with RRC_CELLULAR Flag
#ifdef RRC_CELLULAR
    abstraction_flag = 1;
    ethernet_flag = 1;
#endif

    //make change
    abstraction_flag = 1;

    if (set_sinr == 0)
        sinr_dB = snr_dB - 20;

    // setup ntedevice interface (netlink socket)
    //#ifdef NAS_NETLINK  
    LOG_I(EMU, "[INIT] Starting NAS netlink interface\n");
    ret = netlink_init();
    //#endif



    NB_UE_INST = oai_emulation.info.nb_ue_local + oai_emulation.info.nb_ue_remote;
    NB_eNB_INST = oai_emulation.info.nb_enb_local + oai_emulation.info.nb_enb_remote;



    if (set_seed) {
        randominit(oai_emulation.info.seed);
        set_taus_seed(oai_emulation.info.seed);
    } else {
        randominit(0);
        set_taus_seed(0);
    }
    // change the nb_connected_eNB
    init_lte_vars(&frame_parms, oai_emulation.info.frame_type, oai_emulation.info.tdd_config, oai_emulation.info.tdd_config_S, oai_emulation.info.extended_prefix_flag, oai_emulation.info.N_RB_DL, Nid_cell, cooperation_flag, oai_emulation.info.transmission_mode, abstraction_flag);

    printf("AFTER init: Nid_cell %d\n", PHY_vars_eNB_g[0]->lte_frame_parms.Nid_cell);
    printf("AFTER init: frame_type %d,tdd_config %d\n",
            PHY_vars_eNB_g[0]->lte_frame_parms.frame_type,
            PHY_vars_eNB_g[0]->lte_frame_parms.tdd_config);


    /* Added for PHY abstraction */
    //make change
    if (abstraction_flag)
        //get_beta_map();

        for (eNB_id = 0; eNB_id < NB_eNB_INST; eNB_id++) {
            enb_data[eNB_id] = (node_desc_t *) malloc(sizeof (node_desc_t));
            init_enb(enb_data[eNB_id], oai_emulation.environment_system_config.antenna.eNB_antenna);
        }

    for (UE_id = 0; UE_id < NB_UE_INST; UE_id++) {
        ue_data[UE_id] = (node_desc_t *) malloc(sizeof (node_desc_t));
        init_ue(ue_data[UE_id], oai_emulation.environment_system_config.antenna.UE_antenna);
    }



    // initialize channel descriptors
    for (eNB_id = 0; eNB_id < NB_eNB_INST; eNB_id++) {
        for (UE_id = 0; UE_id < NB_UE_INST; UE_id++) {
            LOG_D(OCM, "Initializing channel (%s, %d) from eNB %d to UE %d\n", oai_emulation.environment_system_config.fading.small_scale.selected_option,
                    map_str_to_int(small_scale_names, oai_emulation.environment_system_config.fading.small_scale.selected_option), eNB_id, UE_id);

            eNB2UE[eNB_id][UE_id] = new_channel_desc_scm(PHY_vars_eNB_g[eNB_id]->lte_frame_parms.nb_antennas_tx,
                    PHY_vars_UE_g[UE_id]->lte_frame_parms.nb_antennas_rx,
                    map_str_to_int(small_scale_names, oai_emulation.environment_system_config.fading.small_scale.selected_option),
                    oai_emulation.environment_system_config.system_bandwidth_MB,
                    forgetting_factor,
                    0,
                    0);
            random_channel(eNB2UE[eNB_id][UE_id]);
            LOG_D(OCM, "[SIM] Initializing channel (%s, %d) from UE %d to eNB %d\n", oai_emulation.environment_system_config.fading.small_scale.selected_option,
                    map_str_to_int(small_scale_names, oai_emulation.environment_system_config.fading.small_scale.selected_option), UE_id, eNB_id);
            UE2eNB[UE_id][eNB_id] = new_channel_desc_scm(PHY_vars_UE_g[UE_id]->lte_frame_parms.nb_antennas_tx,
                    PHY_vars_eNB_g[eNB_id]->lte_frame_parms.nb_antennas_rx,
                    map_str_to_int(small_scale_names, oai_emulation.environment_system_config.fading.small_scale.selected_option),
                    oai_emulation.environment_system_config.system_bandwidth_MB,
                    forgetting_factor,
                    0,
                    0);
            random_channel(UE2eNB[UE_id][eNB_id]);
        }
    }

    //  Not needed anymore, done automatically in init_freq_channel upon first call to the function
    freq_channel(eNB2UE[0][0], PHY_vars_UE_g[0]->lte_frame_parms.N_RB_DL, PHY_vars_UE_g[0]->lte_frame_parms.N_RB_DL * 12 + 1);
    number_of_cards = 1;

    openair_daq_vars.rx_rf_mode = 1;
    openair_daq_vars.tdd = 1;
    openair_daq_vars.rx_gain_mode = DAQ_AGC_ON;

    openair_daq_vars.dlsch_transmission_mode = oai_emulation.info.transmission_mode;

    openair_daq_vars.target_ue_dl_mcs = target_dl_mcs;
    openair_daq_vars.target_ue_ul_mcs = target_ul_mcs;
    openair_daq_vars.dlsch_rate_adaptation = rate_adaptation_flag;
    openair_daq_vars.ue_ul_nb_rb = 2;

    for (UE_id = 0; UE_id < NB_UE_INST; UE_id++) {
        PHY_vars_UE_g[UE_id]->rx_total_gain_dB = 120;
        // update UE_mode for each eNB_id not just 0
        if (abstraction_flag == 0)
            PHY_vars_UE_g[UE_id]->UE_mode[0] = NOT_SYNCHED;
        else {
            // 0 is the index of the connected eNB
            PHY_vars_UE_g[UE_id]->UE_mode[0] = PRACH;
        }
        PHY_vars_UE_g[UE_id]->lte_ue_pdcch_vars[0]->crnti = 0x1235 + UE_id;
        PHY_vars_UE_g[UE_id]->current_dlsch_cqi[0] = 10;

        LOG_I(EMU, "UE %d mode is initialized to %d\n", UE_id, PHY_vars_UE_g[UE_id]->UE_mode[0]);
    }


    printf("before L2 init: Nid_cell %d\n", PHY_vars_eNB_g[0]->lte_frame_parms.Nid_cell);
    printf("before L2 init: frame_type %d,tdd_config %d\n",
            PHY_vars_eNB_g[0]->lte_frame_parms.frame_type,
            PHY_vars_eNB_g[0]->lte_frame_parms.tdd_config);



#ifdef OPENAIR2
    l2_init(&PHY_vars_eNB_g[0]->lte_frame_parms);
    printf("after L2 init: Nid_cell %d\n", PHY_vars_eNB_g[0]->lte_frame_parms.Nid_cell);
    printf("after L2 init: frame_type %d,tdd_config %d\n",
            PHY_vars_eNB_g[0]->lte_frame_parms.frame_type,
            PHY_vars_eNB_g[0]->lte_frame_parms.tdd_config);


    for (i = 0; i < NB_eNB_INST; i++)
        mac_xface->mrbch_phy_sync_failure(i, 0, i);


    if (abstraction_flag == 1) {
        for (UE_id = 0; UE_id < NB_UE_INST; UE_id++)
            mac_xface->dl_phy_sync_success(UE_id, 0, 0, 1); //UE_id%NB_eNB_INST);
    }
#endif

    mac_xface->macphy_exit = exit_fun;

    // time calibration for OAI 
    clock_gettime(CLOCK_REALTIME, &time_spec);
    time_now = (unsigned long) time_spec.tv_nsec;
    td_avg = 0;
    sleep_time_us = SLEEP_STEP_US;
    td_avg = TARGET_SF_TIME_NS;


    LOG_I(EMU, ">>>>>>>>>>>>>>>>>>>>>>>>>>> OAIEMU initialization done <<<<<<<<<<<<<<<<<<<<<<<<<<\n\n");
    printf("after init: Nid_cell %d\n", PHY_vars_eNB_g[0]->lte_frame_parms.Nid_cell);
    printf("after init: frame_type %d,tdd_config %d\n",
            PHY_vars_eNB_g[0]->lte_frame_parms.frame_type,
            PHY_vars_eNB_g[0]->lte_frame_parms.tdd_config);



    ////////////////////////////////////////////////////
    /*make change*/
    eNB_rrc_inst[0].Info.Status[0] = 3;
    eNB_rrc_inst[0].Info.Status[1] = 3;
    //  eNB_rrc_inst[0].Info.Status[2] = 3;
    //  eNB_rrc_inst[0].Info.Status[3] = 3;

    eNB_mac_inst[0].UE_template[0].rnti = 1;


    //make change add ip
    //eNB
    int ii;
    for (ii = 0; ii < 16; ii++) {
        rlc[ii].m_rlc_pointer[3].rlc_type = RLC_AM;
    }

    rlc_info_t Rlc_info_am_config;
    Rlc_info_am_config.rlc_mode = RLC_AM;
    Rlc_info_am_config.rlc.rlc_am_info.max_retx_threshold = 50;
    Rlc_info_am_config.rlc.rlc_am_info.poll_pdu = 8;
    Rlc_info_am_config.rlc.rlc_am_info.poll_byte = 1000;
    Rlc_info_am_config.rlc.rlc_am_info.t_poll_retransmit = 15;
    Rlc_info_am_config.rlc.rlc_am_info.t_reordering = 50;
    Rlc_info_am_config.rlc.rlc_am_info.t_status_prohibit = 10;
    config_req_rlc_am(&rlc[0].m_rlc_am_array[rlc[0].m_rlc_pointer[3].rlc_index],
            0, 1, 0, &Rlc_info_am_config.rlc.rlc_am_info, 3, 2);


    buff_p = (struct buffer_for_send*) malloc(sizeof (struct buffer_for_send));
    sem_init(&ACKOK, 0, 0);
    pthread_t tid1;
    int err;

    //  err = pthread_create (&tid1, NULL, socket_recieve, NULL);

    //make change add phy
    int dl_length = 0;

    if ((shmid = creatshm("/home/cic/", 57, SHM_SIZE)) == -1) //create or get shared memory
        return -1;
    /*create process and shared memory*/
    if ((shmaddr = shmat(shmid, (char*) 0, 0)) == (char *) - 1) {
        perror("attch shared memory error!\n");
        exit(1);
    }
    if ((semid = creatsem("/home/cic/", 39, 1, 0)) == -1)//create sem
        return -1;

    //msgid = msgget((key_t)1235, 0666 | IPC_CREAT);  
    msgid = msgget((key_t) 1234, 0666 | IPC_CREAT);
    if (msgid == -1) {
        fprintf(stderr, "msgget failed with error: %d\n", errno);
        exit(EXIT_FAILURE);
    }

    int is_empty = 0;

    while (is_empty != -1) {
        is_empty = msgrcv(msgid, (void*) &data, BUFSIZ, msgtype, IPC_NOWAIT);
    }

    memcpy(shmaddr, &dl_length, sizeof (int));
    ////////////////////////////////////////////////////

    //ue
    UE_rrc_inst[0].Info[0].State = 3;
    config_req_rlc_am(&rlc[1].m_rlc_am_array[rlc[1].m_rlc_pointer[3].rlc_index],
            1, 0, 1, &Rlc_info_am_config.rlc.rlc_am_info, 3, 2);
    ////////////////////////////////////////





    if (ue_connection_test == 1) {
        snr_direction = -1;
        snr_dB = 20;
        sinr_dB = -20;
    }
    for (frame = 0; frame < oai_emulation.info.n_frames; frame++) {
        //clock_gettime(CLOCK_REALTIME, &start_time);
        if (ue_connection_test == 1) {
            if ((frame % 20) == 0) {
                snr_dB += snr_direction;
                sinr_dB -= snr_direction;
            }
            if (snr_dB == -20) {
                snr_direction = 1;
            } else if (snr_dB == 20) {
                snr_direction = -1;
            }
        }

        oai_emulation.info.frame = frame;
        //oai_emulation.info.time_ms += 1;  
        oai_emulation.info.time_s += 0.1; // emu time in s, each frame lasts for 10 ms // JNote: TODO check the coherency of the time and frame (I corrected it to 10 (instead of 0.01)
        // if n_frames not set by the user or is greater than max num frame then set adjust the frame counter
        if ((oai_emulation.info.n_frames_flag == 0) || (oai_emulation.info.n_frames >= 0xffff)) {
            frame %= (oai_emulation.info.n_frames - 1);
        }

        if ((frame % 10) == 0) { // call OMG every 10ms 
            update_nodes(oai_emulation.info.time_s);
            display_node_list(enb_node_list);
            display_node_list(ue_node_list);
            if (oai_emulation.info.omg_model_ue >= MAX_NUM_MOB_TYPES) { // mix mobility model
                for (UE_id = oai_emulation.info.first_ue_local; UE_id < (oai_emulation.info.first_ue_local + oai_emulation.info.nb_ue_local); UE_id++) {
                    new_omg_model = randomGen(STATIC, RWALK);
                    LOG_D(OMG, "[UE] Node of ID %d is changing mobility generator ->%d \n", UE_id, new_omg_model);
                    // reset the mobility model for a specific node
                    set_new_mob_type(UE_id, UE, new_omg_model, oai_emulation.info.time_s);
                }
            }
            if (oai_emulation.info.omg_model_enb >= MAX_NUM_MOB_TYPES) { // mix mobility model
                for (eNB_id = oai_emulation.info.first_enb_local; eNB_id < (oai_emulation.info.first_enb_local + oai_emulation.info.nb_enb_local); eNB_id++) {
                    new_omg_model = randomGen(STATIC, RWALK);
                    LOG_D(OMG, "[eNB] Node of ID %d is changing mobility generator ->%d \n", UE_id, new_omg_model);
                    // reset the mobility model for a specific node
                    set_new_mob_type(eNB_id, eNB, new_omg_model, oai_emulation.info.time_s);
                }
            }
        }

        enb_node_list = get_current_positions(oai_emulation.info.omg_model_enb, eNB, oai_emulation.info.time_s);
        ue_node_list = get_current_positions(oai_emulation.info.omg_model_ue, UE, oai_emulation.info.time_s);
        // check if pipe is still open
        if ((oai_emulation.info.omv_enabled == 1)) {
            omv_write(pfd[1], enb_node_list, ue_node_list, omv_data);
        }

        for (eNB_id = 0; eNB_id < NB_eNB_INST; eNB_id++)
            enb_data[eNB_id]->tx_power_dBm = PHY_vars_eNB_g[eNB_id]->lte_frame_parms.pdsch_config_common.referenceSignalPower;
        for (UE_id = 0; UE_id < NB_UE_INST; UE_id++)
            ue_data[UE_id]->tx_power_dBm = PHY_vars_UE_g[UE_id]->tx_power_dBm;

        /* check if the openair channel model is activated used for PHY abstraction : path loss*/
        if ((oai_emulation.info.ocm_enabled == 1)&& (ethernet_flag == 0)) {
            //LOG_D(OMG," extracting position of eNb...\n");
            extract_position(enb_node_list, enb_data, NB_eNB_INST);
            //LOG_D(OMG," extracting position of UE...\n");
            //      if (oai_emulation.info.omg_model_ue == TRACE)
            extract_position(ue_node_list, ue_data, NB_UE_INST);

            for (eNB_id = 0; eNB_id < NB_eNB_INST; eNB_id++) {
                for (UE_id = 0; UE_id < NB_UE_INST; UE_id++) {
                    calc_path_loss(enb_data[eNB_id], ue_data[UE_id], eNB2UE[eNB_id][UE_id], oai_emulation.environment_system_config, ShaF);
                    //calc_path_loss (enb_data[eNB_id], ue_data[UE_id], eNB2UE[eNB_id][UE_id], oai_emulation.environment_system_config,0);
                    UE2eNB[UE_id][eNB_id]->path_loss_dB = eNB2UE[eNB_id][UE_id]->path_loss_dB;
                    LOG_D(OCM, "Path loss between eNB %d at (%f,%f) and UE %d at (%f,%f) is %f, angle %f\n",
                            eNB_id, enb_data[eNB_id]->x, enb_data[eNB_id]->y, UE_id, ue_data[UE_id]->x, ue_data[UE_id]->y,
                            eNB2UE[eNB_id][UE_id]->path_loss_dB, eNB2UE[eNB_id][UE_id]->aoa);
                }
            }
        } else {
            for (eNB_id = 0; eNB_id < NB_eNB_INST; eNB_id++) {
                for (UE_id = 0; UE_id < NB_UE_INST; UE_id++) {
                    //UE2eNB[UE_id][eNB_id]->path_loss_dB = -105 + snr_dB;
                    if (eNB_id == (UE_id % NB_eNB_INST)) {
                        eNB2UE[eNB_id][UE_id]->path_loss_dB = -105 + snr_dB - PHY_vars_eNB_g[eNB_id]->lte_frame_parms.pdsch_config_common.referenceSignalPower;
                        UE2eNB[UE_id][eNB_id]->path_loss_dB = -105 + snr_dB - PHY_vars_eNB_g[eNB_id]->lte_frame_parms.pdsch_config_common.referenceSignalPower; //+20 to offset the difference in tx power of the UE wrt eNB
                    } else {
                        eNB2UE[eNB_id][UE_id]->path_loss_dB = -105 + sinr_dB - PHY_vars_eNB_g[eNB_id]->lte_frame_parms.pdsch_config_common.referenceSignalPower;
                        UE2eNB[UE_id][eNB_id]->path_loss_dB = -105 + sinr_dB - PHY_vars_eNB_g[eNB_id]->lte_frame_parms.pdsch_config_common.referenceSignalPower;
                    }
                    LOG_I(OCM, "Path loss from eNB %d to UE %d => %f dB (eNB TX %d)\n", eNB_id, UE_id, eNB2UE[eNB_id][UE_id]->path_loss_dB,
                            PHY_vars_eNB_g[eNB_id]->lte_frame_parms.pdsch_config_common.referenceSignalPower);
                }
            }
        }

        for (slot = 0; slot < 20; slot++) {
            //clock_gettime(CLOCK_PROCESS_CPUTIME_ID, &start_time);
            last_slot = (slot - 1) % 20;
            if (last_slot < 0)
                last_slot += 20;
            next_slot = (slot + 1) % 20;

            oai_emulation.info.time_ms = frame * 10 + (next_slot >> 1);

            direction = subframe_select(frame_parms, next_slot >> 1);

            if (Channel_Flag == 0) {
                if ((next_slot % 2) == 0)
                    clear_eNB_transport_info(oai_emulation.info.nb_enb_local);

                for (eNB_id = oai_emulation.info.first_enb_local;
                        (eNB_id < (oai_emulation.info.first_enb_local + oai_emulation.info.nb_enb_local)) && (oai_emulation.info.cli_start_enb[eNB_id] == 1);
                        eNB_id++) {
                    //printf ("debug: Nid_cell %d\n", PHY_vars_eNB_g[eNB_id]->lte_frame_parms.Nid_cell);
                    //printf ("debug: frame_type %d,tdd_config %d\n", PHY_vars_eNB_g[eNB_id]->lte_frame_parms.frame_type,PHY_vars_eNB_g[eNB_id]->lte_frame_parms.tdd_config);
                    LOG_D(EMU, "PHY procedures eNB %d for frame %d, slot %d (subframe TX %d, RX %d) TDD %d/%d Nid_cell %d\n",
                            eNB_id, frame, slot, next_slot >> 1, last_slot >> 1,
                            PHY_vars_eNB_g[eNB_id]->lte_frame_parms.frame_type,
                            PHY_vars_eNB_g[eNB_id]->lte_frame_parms.tdd_config, PHY_vars_eNB_g[eNB_id]->lte_frame_parms.Nid_cell);

                    PHY_vars_eNB_g[eNB_id]->frame = frame;
                    //clock_gettime(CLOCK_PROCESS_CPUTIME_ID, &start_time);
                    phy_procedures_eNB_lte(last_slot, next_slot, PHY_vars_eNB_g[eNB_id], abstraction_flag);
                    //clock_gettime(CLOCK_PROCESS_CPUTIME_ID, &end_time);
                    //printf("\nphy_procedures_eNB_lte:%d. %ld\n\n", end_time.tv_sec - start_time.tv_sec, end_time.tv_nsec - start_time.tv_nsec);
                }
                // Call ETHERNET emulation here
                //emu_transport (frame, last_slot, next_slot, direction, oai_emulation.info.frame_type, ethernet_flag);

                if ((next_slot % 2) == 0)
                    clear_UE_transport_info(oai_emulation.info.nb_ue_local);

                for (UE_id = oai_emulation.info.first_ue_local;
                        (UE_id < (oai_emulation.info.first_ue_local + oai_emulation.info.nb_ue_local)) && (oai_emulation.info.cli_start_ue[UE_id] == 1);
                        UE_id++)
                    if (frame >= (UE_id * 20)) {
                        // activate UE only after 20*UE_id frames so that different UEs turn on separately
                        LOG_D(EMU, "PHY procedures UE %d for frame %d, slot %d (subframe TX %d, RX %d)\n",
                                UE_id, frame, slot, next_slot >> 1, last_slot >> 1);

                        PHY_vars_UE_g[UE_id]->UE_mode[0] = 2;
                        if (PHY_vars_UE_g[UE_id]->UE_mode[0] != NOT_SYNCHED) {
                            if (frame > 0) {
                                PHY_vars_UE_g[UE_id]->frame = frame;
                                //phy_procedures_UE_lte (last_slot, next_slot, PHY_vars_UE_g[UE_id], 0, abstraction_flag,normal_txrx);
                            }
                        } else {
                            if (abstraction_flag == 1) {
                                LOG_E(EMU, "sync not supported in abstraction mode (UE%d,mode%d)\n", UE_id, PHY_vars_UE_g[UE_id]->UE_mode[0]);
                                exit(-1);
                            }
                            if ((frame > 0) && (last_slot == (LTE_SLOTS_PER_FRAME - 2))) {
                                initial_sync(PHY_vars_UE_g[UE_id], normal_txrx);
                            }
                        }
                    }

                emu_transport(frame, last_slot, next_slot, direction, oai_emulation.info.frame_type, ethernet_flag);
                if ((direction == SF_DL) || (frame_parms->frame_type == 0)) {
                    //error to run
                    //	do_DL_sig(r_re0,r_im0,r_re,r_im,s_re,s_im,eNB2UE,enb_data,ue_data,next_slot,abstraction_flag,frame_parms);
                    //
                }
                if ((direction == SF_UL) || (frame_parms->frame_type == 0)) {
                    //	do_UL_sig(r_re0,r_im0,r_re,r_im,s_re,s_im,UE2eNB,enb_data,ue_data,next_slot,abstraction_flag,frame_parms);
                }
                if ((direction == SF_S)) {
                    //it must be a special subframe
                    if (next_slot % 2 == 0) {
                        //DL part
                        //	    do_DL_sig(r_re0,r_im0,r_re,r_im,s_re,s_im,eNB2UE,enb_data,ue_data,next_slot,abstraction_flag,frame_parms);
                    } else {
                        // UL part
                        //	    do_UL_sig(r_re0,r_im0,r_re,r_im,s_re,s_im,UE2eNB,enb_data,ue_data,next_slot,abstraction_flag,frame_parms);
                    }
                }

                if ((last_slot == 1) && (frame == 0)
                        && (abstraction_flag == 0) && (oai_emulation.info.n_frames == 1)) {

                    write_output("dlchan0.m", "dlch0",
                            &(PHY_vars_UE_g[0]->lte_ue_common_vars.dl_ch_estimates[0][0][0]),
                            (6 * (PHY_vars_UE_g[0]->lte_frame_parms.ofdm_symbol_size)), 1, 1);
                    write_output("dlchan1.m", "dlch1",
                            &(PHY_vars_UE_g[0]->lte_ue_common_vars.dl_ch_estimates[1][0][0]),
                            (6 * (PHY_vars_UE_g[0]->lte_frame_parms.ofdm_symbol_size)), 1, 1);
                    write_output("dlchan2.m", "dlch2",
                            &(PHY_vars_UE_g[0]->lte_ue_common_vars.dl_ch_estimates[2][0][0]),
                            (6 * (PHY_vars_UE_g[0]->lte_frame_parms.ofdm_symbol_size)), 1, 1);
                    write_output("pbch_rxF_comp0.m", "pbch_comp0",
                            PHY_vars_UE_g[0]->lte_ue_pbch_vars[0]->rxdataF_comp[0], 6 * 12 * 4, 1, 1);
                    write_output("pbch_rxF_llr.m", "pbch_llr",
                            PHY_vars_UE_g[0]->lte_ue_pbch_vars[0]->llr, (frame_parms->Ncp == 0) ? 1920 : 1728, 1, 4);
                }


                if (next_slot % 2 == 0) {
                    clock_gettime(CLOCK_REALTIME, &time_spec);
                    time_last = time_now;
                    time_now = (unsigned long) time_spec.tv_nsec;
                    td = (int) (time_now - time_last);
                    if (td > 0) {
                        td_avg = (int) (((K * (long) td) + (((1 << 3) - K)*((long) td_avg))) >> 3); // in us
                        LOG_D(EMU, "sleep frame %d, time_now %ldus,time_last %ldus,average time difference %ldns, CURRENT TIME DIFF %dus, avgerage difference from the target %dus\n",
                                frame, time_now, time_last, td_avg, td / 1000, (td_avg - TARGET_SF_TIME_NS) / 1000);
                    }
                    if (td_avg < (TARGET_SF_TIME_NS - SF_DEVIATION_OFFSET_NS)) {
                        sleep_time_us += SLEEP_STEP_US;
                        LOG_D(EMU, "[TIMING]Fater than realtime increase the avg sleep time for %d us, frame %d, time_now %ldus,time_last %ldus,average time difference %ldns, CURRENT TIME DIFF %dus, avgerage difference from the target %dus\n", sleep_time_us, frame, time_now, time_last, td_avg, td / 1000, (td_avg - TARGET_SF_TIME_NS) / 1000);
                    } else if (td_avg > (TARGET_SF_TIME_NS + SF_DEVIATION_OFFSET_NS)) {
                        sleep_time_us -= SLEEP_STEP_US;
                        LOG_D(EMU, "[TIMING]Slower than realtime reduce the avg sleep time for %d us, frame %d, time_now %ldus,time_last %ldus,average time difference %ldns, CURRENT TIME DIFF %dus, avgerage difference from the target %dus\n", sleep_time_us, frame, time_now, time_last, td_avg, td / 1000, (td_avg - TARGET_SF_TIME_NS) / 1000);
                    }
                } // end if next_slot%2
            }// if Channel_Flag==0
            //clock_gettime(CLOCK_REALTIME, &end_time);
            //printf("\nslot:%d. %ld\n\n", end_time.tv_sec - start_time.tv_sec, end_time.tv_nsec - start_time.tv_nsec);

        } //end of slot

        if ((frame >= 1)&&(frame <= 9)&&(abstraction_flag == 0)&&(Channel_Flag == 0)) {
            write_output("UEtxsig0.m", "txs0", PHY_vars_UE_g[0]->lte_ue_common_vars.txdata[0], FRAME_LENGTH_COMPLEX_SAMPLES, 1, 1);
            sprintf(fname, "eNBtxsig%d.m", frame);
            sprintf(vname, "txs%d", frame);
            write_output(fname, vname, PHY_vars_eNB_g[0]->lte_eNB_common_vars.txdata[0][0], FRAME_LENGTH_COMPLEX_SAMPLES, 1, 1);
            write_output("eNBtxsigF0.m", "txsF0", PHY_vars_eNB_g[0]->lte_eNB_common_vars.txdataF[0][0], PHY_vars_eNB_g[0]->lte_frame_parms.symbols_per_tti * PHY_vars_eNB_g[0]->lte_frame_parms.ofdm_symbol_size, 1, 1);

            write_output("UErxsig0.m", "rxs0", PHY_vars_UE_g[0]->lte_ue_common_vars.rxdata[0], FRAME_LENGTH_COMPLEX_SAMPLES, 1, 1);
            write_output("eNBrxsig0.m", "rxs0", PHY_vars_eNB_g[0]->lte_eNB_common_vars.rxdata[0][0], FRAME_LENGTH_COMPLEX_SAMPLES, 1, 1);
        }


        // calibrate at the end of each frame if there is some time  left
        if ((sleep_time_us > 0)&& (ethernet_flag == 0)) {
            LOG_I(EMU, "[TIMING] Adjust average frame duration, sleep for %d us\n", sleep_time_us);
            usleep(sleep_time_us);
            sleep_time_us = 0; // reset the timer, could be done per n SF 
        }
        clock_gettime(CLOCK_PROCESS_CPUTIME_ID, &end_time);
        printf("\nframe:%d. %ld\n\n", end_time.tv_sec - start_time.tv_sec, end_time.tv_nsec - start_time.tv_nsec);
    } //end of frame
    printf("oai_emulation.info.otg_enabled:%d\n", oai_emulation.info.otg_enabled);
    printf("ethernet_flag:%d\n", ethernet_flag);
    printf("oai_emulation.info.ocm_enabled:%d\n", oai_emulation.info.ocm_enabled);
    LOG_I(EMU, ">>>>>>>>>>>>>>>>>>>>>>>>>>> OAIEMU Ending <<<<<<<<<<<<<<<<<<<<<<<<<<\n\n");

    //Perform KPI measurements
    if (oai_emulation.info.otg_enabled == 1)
        kpi_gen();

    // relase all rx state
    if (ethernet_flag == 1) {
        emu_transport_release();
    }

    if (abstraction_flag == 0 && Channel_Flag == 0 && Process_Flag == 0) {
        /*
           #ifdef IFFT_FPGA
           free(txdataF2[0]);
           free(txdataF2[1]);
           free(txdataF2);
           free(txdata[0]);
           free(txdata[1]);
           free(txdata);
           #endif
         */

        for (i = 0; i < 2; i++) {
            free(s_re[i]);
            free(s_im[i]);
            free(r_re[i]);
            free(r_im[i]);
        }
        free(s_re);
        free(s_im);
        free(r_re);
        free(r_im);

        lte_sync_time_free();
    }
    //  pthread_join(sigth, NULL);

    // added for PHY abstraction
    if (oai_emulation.info.ocm_enabled == 1) {
        for (eNB_id = 0; eNB_id < NUMBER_OF_eNB_MAX; eNB_id++)
            free(enb_data[eNB_id]);

        for (UE_id = 0; UE_id < NUMBER_OF_UE_MAX; UE_id++)
            free(ue_data[UE_id]);
    } //End of PHY abstraction changes

#ifdef OPENAIR2
    mac_top_cleanup();
#endif 

#ifdef PRINT_STATS
    for (UE_id = 0; UE_id < NB_UE_INST; UE_id++)
        if (UE_stats[UE_id])
            fclose(UE_stats[UE_id]);
    if (eNB_stats)
        fclose(eNB_stats);
    if (eNB_avg_thr)
        fclose(eNB_avg_thr);
#endif

    // stop OMG
    stop_mobility_generator(oai_emulation.info.omg_model_ue); //omg_param_list.mobility_type
    if (oai_emulation.info.omv_enabled == 1)
        omv_end(pfd[1], omv_data);

    if ((oai_emulation.info.ocm_enabled == 1) && (ethernet_flag == 0) && (ShaF != NULL))
        destroyMat(ShaF, map1, map2);

    if ((oai_emulation.info.opt_enabled == 1))
        terminate_opt();

    if (oai_emulation.info.cli_enabled)
        cli_server_cleanup();
    //bring oai if down
    terminate();
    logClean();
    return (0);
}

static void *sigh(void *arg) {

    int signum;
    sigset_t sigcatch;
    sigemptyset(&sigcatch);
    sigaddset(&sigcatch, SIGHUP);
    sigaddset(&sigcatch, SIGINT);
    sigaddset(&sigcatch, SIGTERM);

    for (;;) {
        sigwait(&sigcatch, &signum);
        switch (signum) {
            case SIGHUP:
            case SIGINT:
            case SIGTERM:
                terminate();
            default:
                break;
        }
    }
    pthread_exit(NULL);
}

void terminate(void) {
    int i;
    char interfaceName[8];
    for (i = 0; i < NUMBER_OF_eNB_MAX + NUMBER_OF_UE_MAX; i++)
        if (oai_emulation.info.oai_ifup[i] == 1) {
            sprintf(interfaceName, "oai%d", i);
            bringInterfaceUp(interfaceName, 0);
        }
}

void exit_fun(const char* s) {
    fprintf(stderr, "Error: %s. Exiting!\n", s);
    exit(-1);
}
