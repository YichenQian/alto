/*
 * Generated by asn1c-0.9.22 (http://lionet.info/asn1c)
 * From ASN.1 module "EUTRA-RRC-Definitions"
 * 	found in "/home/liushifu/openair4G/openair2/RRC/LITE/MESSAGES/asn1c/ASN1_files/EUTRA-RRC-Definitions.asn"
 * 	`asn1c -gen-PER -fcompound-names -fnative-types`
 */

#ifndef	_DRX_Config_H_
#define	_DRX_Config_H_


#include <asn_application.h>

/* Including external dependencies */
#include <NULL.h>
#include <NativeEnumerated.h>
#include <NativeInteger.h>
#include <constr_CHOICE.h>
#include <constr_SEQUENCE.h>

#ifdef __cplusplus
extern "C" {
#endif

/* Dependencies */
typedef enum DRX_Config_PR {
	DRX_Config_PR_NOTHING,	/* No components present */
	DRX_Config_PR_release,
	DRX_Config_PR_setup
} DRX_Config_PR;
typedef enum DRX_Config__setup__onDurationTimer {
	DRX_Config__setup__onDurationTimer_psf1	= 0,
	DRX_Config__setup__onDurationTimer_psf2	= 1,
	DRX_Config__setup__onDurationTimer_psf3	= 2,
	DRX_Config__setup__onDurationTimer_psf4	= 3,
	DRX_Config__setup__onDurationTimer_psf5	= 4,
	DRX_Config__setup__onDurationTimer_psf6	= 5,
	DRX_Config__setup__onDurationTimer_psf8	= 6,
	DRX_Config__setup__onDurationTimer_psf10	= 7,
	DRX_Config__setup__onDurationTimer_psf20	= 8,
	DRX_Config__setup__onDurationTimer_psf30	= 9,
	DRX_Config__setup__onDurationTimer_psf40	= 10,
	DRX_Config__setup__onDurationTimer_psf50	= 11,
	DRX_Config__setup__onDurationTimer_psf60	= 12,
	DRX_Config__setup__onDurationTimer_psf80	= 13,
	DRX_Config__setup__onDurationTimer_psf100	= 14,
	DRX_Config__setup__onDurationTimer_psf200	= 15
} e_DRX_Config__setup__onDurationTimer;
typedef enum DRX_Config__setup__drx_InactivityTimer {
	DRX_Config__setup__drx_InactivityTimer_psf1	= 0,
	DRX_Config__setup__drx_InactivityTimer_psf2	= 1,
	DRX_Config__setup__drx_InactivityTimer_psf3	= 2,
	DRX_Config__setup__drx_InactivityTimer_psf4	= 3,
	DRX_Config__setup__drx_InactivityTimer_psf5	= 4,
	DRX_Config__setup__drx_InactivityTimer_psf6	= 5,
	DRX_Config__setup__drx_InactivityTimer_psf8	= 6,
	DRX_Config__setup__drx_InactivityTimer_psf10	= 7,
	DRX_Config__setup__drx_InactivityTimer_psf20	= 8,
	DRX_Config__setup__drx_InactivityTimer_psf30	= 9,
	DRX_Config__setup__drx_InactivityTimer_psf40	= 10,
	DRX_Config__setup__drx_InactivityTimer_psf50	= 11,
	DRX_Config__setup__drx_InactivityTimer_psf60	= 12,
	DRX_Config__setup__drx_InactivityTimer_psf80	= 13,
	DRX_Config__setup__drx_InactivityTimer_psf100	= 14,
	DRX_Config__setup__drx_InactivityTimer_psf200	= 15,
	DRX_Config__setup__drx_InactivityTimer_psf300	= 16,
	DRX_Config__setup__drx_InactivityTimer_psf500	= 17,
	DRX_Config__setup__drx_InactivityTimer_psf750	= 18,
	DRX_Config__setup__drx_InactivityTimer_psf1280	= 19,
	DRX_Config__setup__drx_InactivityTimer_psf1920	= 20,
	DRX_Config__setup__drx_InactivityTimer_psf2560	= 21,
	DRX_Config__setup__drx_InactivityTimer_spare10	= 22,
	DRX_Config__setup__drx_InactivityTimer_spare9	= 23,
	DRX_Config__setup__drx_InactivityTimer_spare8	= 24,
	DRX_Config__setup__drx_InactivityTimer_spare7	= 25,
	DRX_Config__setup__drx_InactivityTimer_spare6	= 26,
	DRX_Config__setup__drx_InactivityTimer_spare5	= 27,
	DRX_Config__setup__drx_InactivityTimer_spare4	= 28,
	DRX_Config__setup__drx_InactivityTimer_spare3	= 29,
	DRX_Config__setup__drx_InactivityTimer_spare2	= 30,
	DRX_Config__setup__drx_InactivityTimer_spare1	= 31
} e_DRX_Config__setup__drx_InactivityTimer;
typedef enum DRX_Config__setup__drx_RetransmissionTimer {
	DRX_Config__setup__drx_RetransmissionTimer_psf1	= 0,
	DRX_Config__setup__drx_RetransmissionTimer_psf2	= 1,
	DRX_Config__setup__drx_RetransmissionTimer_psf4	= 2,
	DRX_Config__setup__drx_RetransmissionTimer_psf6	= 3,
	DRX_Config__setup__drx_RetransmissionTimer_psf8	= 4,
	DRX_Config__setup__drx_RetransmissionTimer_psf16	= 5,
	DRX_Config__setup__drx_RetransmissionTimer_psf24	= 6,
	DRX_Config__setup__drx_RetransmissionTimer_psf33	= 7
} e_DRX_Config__setup__drx_RetransmissionTimer;
typedef enum DRX_Config__setup__longDRX_CycleStartOffset_PR {
	DRX_Config__setup__longDRX_CycleStartOffset_PR_NOTHING,	/* No components present */
	DRX_Config__setup__longDRX_CycleStartOffset_PR_sf10,
	DRX_Config__setup__longDRX_CycleStartOffset_PR_sf20,
	DRX_Config__setup__longDRX_CycleStartOffset_PR_sf32,
	DRX_Config__setup__longDRX_CycleStartOffset_PR_sf40,
	DRX_Config__setup__longDRX_CycleStartOffset_PR_sf64,
	DRX_Config__setup__longDRX_CycleStartOffset_PR_sf80,
	DRX_Config__setup__longDRX_CycleStartOffset_PR_sf128,
	DRX_Config__setup__longDRX_CycleStartOffset_PR_sf160,
	DRX_Config__setup__longDRX_CycleStartOffset_PR_sf256,
	DRX_Config__setup__longDRX_CycleStartOffset_PR_sf320,
	DRX_Config__setup__longDRX_CycleStartOffset_PR_sf512,
	DRX_Config__setup__longDRX_CycleStartOffset_PR_sf640,
	DRX_Config__setup__longDRX_CycleStartOffset_PR_sf1024,
	DRX_Config__setup__longDRX_CycleStartOffset_PR_sf1280,
	DRX_Config__setup__longDRX_CycleStartOffset_PR_sf2048,
	DRX_Config__setup__longDRX_CycleStartOffset_PR_sf2560
} DRX_Config__setup__longDRX_CycleStartOffset_PR;
typedef enum DRX_Config__setup__shortDRX__shortDRX_Cycle {
	DRX_Config__setup__shortDRX__shortDRX_Cycle_sf2	= 0,
	DRX_Config__setup__shortDRX__shortDRX_Cycle_sf5	= 1,
	DRX_Config__setup__shortDRX__shortDRX_Cycle_sf8	= 2,
	DRX_Config__setup__shortDRX__shortDRX_Cycle_sf10	= 3,
	DRX_Config__setup__shortDRX__shortDRX_Cycle_sf16	= 4,
	DRX_Config__setup__shortDRX__shortDRX_Cycle_sf20	= 5,
	DRX_Config__setup__shortDRX__shortDRX_Cycle_sf32	= 6,
	DRX_Config__setup__shortDRX__shortDRX_Cycle_sf40	= 7,
	DRX_Config__setup__shortDRX__shortDRX_Cycle_sf64	= 8,
	DRX_Config__setup__shortDRX__shortDRX_Cycle_sf80	= 9,
	DRX_Config__setup__shortDRX__shortDRX_Cycle_sf128	= 10,
	DRX_Config__setup__shortDRX__shortDRX_Cycle_sf160	= 11,
	DRX_Config__setup__shortDRX__shortDRX_Cycle_sf256	= 12,
	DRX_Config__setup__shortDRX__shortDRX_Cycle_sf320	= 13,
	DRX_Config__setup__shortDRX__shortDRX_Cycle_sf512	= 14,
	DRX_Config__setup__shortDRX__shortDRX_Cycle_sf640	= 15
} e_DRX_Config__setup__shortDRX__shortDRX_Cycle;

/* DRX-Config */
typedef struct DRX_Config {
	DRX_Config_PR present;
	union DRX_Config_u {
		NULL_t	 release;
		struct DRX_Config__setup {
			long	 onDurationTimer;
			long	 drx_InactivityTimer;
			long	 drx_RetransmissionTimer;
			struct DRX_Config__setup__longDRX_CycleStartOffset {
				DRX_Config__setup__longDRX_CycleStartOffset_PR present;
				union DRX_Config__setup__longDRX_CycleStartOffset_u {
					long	 sf10;
					long	 sf20;
					long	 sf32;
					long	 sf40;
					long	 sf64;
					long	 sf80;
					long	 sf128;
					long	 sf160;
					long	 sf256;
					long	 sf320;
					long	 sf512;
					long	 sf640;
					long	 sf1024;
					long	 sf1280;
					long	 sf2048;
					long	 sf2560;
				} choice;
				
				/* Context for parsing across buffer boundaries */
				asn_struct_ctx_t _asn_ctx;
			} longDRX_CycleStartOffset;
			struct DRX_Config__setup__shortDRX {
				long	 shortDRX_Cycle;
				long	 drxShortCycleTimer;
				
				/* Context for parsing across buffer boundaries */
				asn_struct_ctx_t _asn_ctx;
			} *shortDRX;
			
			/* Context for parsing across buffer boundaries */
			asn_struct_ctx_t _asn_ctx;
		} setup;
	} choice;
	
	/* Context for parsing across buffer boundaries */
	asn_struct_ctx_t _asn_ctx;
} DRX_Config_t;

/* Implementation */
/* extern asn_TYPE_descriptor_t asn_DEF_onDurationTimer_4;	// (Use -fall-defs-global to expose) */
/* extern asn_TYPE_descriptor_t asn_DEF_drx_InactivityTimer_21;	// (Use -fall-defs-global to expose) */
/* extern asn_TYPE_descriptor_t asn_DEF_drx_RetransmissionTimer_54;	// (Use -fall-defs-global to expose) */
/* extern asn_TYPE_descriptor_t asn_DEF_shortDRX_Cycle_81;	// (Use -fall-defs-global to expose) */
extern asn_TYPE_descriptor_t asn_DEF_DRX_Config;

#ifdef __cplusplus
}
#endif

#endif	/* _DRX_Config_H_ */
#include <asn_internal.h>
