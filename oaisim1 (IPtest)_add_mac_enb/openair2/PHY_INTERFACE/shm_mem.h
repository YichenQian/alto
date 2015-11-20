/* 
 * File:   shm_mem.h
 * Author: cic
 *
 * Created on 2015年7月23日, 下午1:32
 */

#define	SHM_MEM_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/sem.h>
#include <sys/shm.h>
#include <error.h>
#include <sys/msg.h>  

#define SHM_SIZE     1024
#define MAX_TEXT 1000

struct msg_st  
{  
    long int msg_type;
    int length;
    char text[MAX_TEXT];  
};  
  
union semun{
    int val;
    struct semid_ds *buf;
    unsigned short *array;
    struct seminfo *buf_info;
    void *pad;
};

static int creatsem(const char *pathname, int proj_id, int members, int init_val)
{
    key_t msgkey;
    int index, sid;
    union semun semopts;
    
    if((msgkey = ftok(pathname, proj_id)) == -1){
        perror("ftok error!\n");
        return -1;
    }
    if((sid = semget(msgkey, members, IPC_CREAT|0666)) == -1){
        perror("semget call failed.\n");
        return -1;
    }
    semopts.val = init_val;
    for(index = 0; index < members; index++){
        semctl(sid, index, SETVAL, semopts);
    }
    
    return sid;
}

static int opensem(const char *pathname, int proj_id)
{
    key_t msgkey;
    int sid;
    
    if((msgkey = ftok(pathname, proj_id)) == -1){
        perror("ftok error!\n");
        return -1;
    }
    
    if((sid = semget(msgkey, 0, 0666)) == -1){
        perror("open semget call failed.\n");
        return -1;
    }
    return sid;
}

static int sem_p(int semid, int index)
{
    //struct sembuf sbuf = {0, -1, SEM_UNDO};

    struct sembuf sbuf = {0, -1, IPC_NOWAIT};
    if(index < 0){
        perror("index of array cannot equals a minus value!\n");
        return -1;
    }
    sbuf.sem_num = index;
    if(semop(semid, &sbuf, 1) == -1){
        perror("A wrong operation to semaphore occurred!\n");
        return -1;
    }
    return 0;
}

static int sem_v(int semid, int index)
{
    //struct sembuf sbuf = {0, 1, SEM_UNDO};

    struct sembuf sbuf = {0, 1, IPC_NOWAIT};
    if(index < 0){
        perror("index of array cannot equals a minus value!\n");
        return -1;
    }
    sbuf.sem_num = index;
    if(semop(semid, &sbuf, 1) == -1){
        perror("A wrong operation to semaphore occurred!\n");
        return -1;
    }
    return 0;
}

static int sem_delete(int semid)
{
    return (semctl(semid, 0, IPC_RMID));
}

static int wait_sem(int semid, int index)
{
    while(semctl(semid, index, GETVAL, 0) == 0)
    {
        //wait_num++;

        usleep(500);
    } 
    //printf("wait_num = %x\n", wait_num);

    return 1;

}

static int creatshm(char *pathname, int proj_id, size_t size)
{
    key_t shmkey;
    int sid;
    
    if((shmkey = ftok(pathname, proj_id)) == -1){
        perror("ftok error!\n");
        return -1;
    }
    if((sid = shmget(shmkey, size, IPC_CREAT|0666)) == -1){
        perror("shm call failed!\n");
        return -1;
    }
    return sid;
}

static int deleteshm(int sid)
{
    void *p = NULL;
    return (shmctl(sid, IPC_RMID, p));
}