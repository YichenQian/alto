/*
 * Generated by asn1c-0.9.22 (http://lionet.info/asn1c)
 * From ASN.1 module "EUTRA-RRC-Definitions"
 * 	found in "/home/liushifu/openair4G/openair2/RRC/LITE/MESSAGES/asn1c/ASN1_files/EUTRA-RRC-Definitions.asn"
 * 	`asn1c -gen-PER -fcompound-names -fnative-types`
 */

#ifndef	_SystemTimeInfoCDMA2000_H_
#define	_SystemTimeInfoCDMA2000_H_


#include <asn_application.h>

/* Including external dependencies */
#include <BOOLEAN.h>
#include <BIT_STRING.h>
#include <constr_CHOICE.h>
#include <constr_SEQUENCE.h>

#ifdef __cplusplus
extern "C" {
#endif

/* Dependencies */
typedef enum SystemTimeInfoCDMA2000__cdma_SystemTime_PR {
	SystemTimeInfoCDMA2000__cdma_SystemTime_PR_NOTHING,	/* No components present */
	SystemTimeInfoCDMA2000__cdma_SystemTime_PR_synchronousSystemTime,
	SystemTimeInfoCDMA2000__cdma_SystemTime_PR_asynchronousSystemTime
} SystemTimeInfoCDMA2000__cdma_SystemTime_PR;

/* SystemTimeInfoCDMA2000 */
typedef struct SystemTimeInfoCDMA2000 {
	BOOLEAN_t	 cdma_EUTRA_Synchronisation;
	struct SystemTimeInfoCDMA2000__cdma_SystemTime {
		SystemTimeInfoCDMA2000__cdma_SystemTime_PR present;
		union SystemTimeInfoCDMA2000__cdma_SystemTime_u {
			BIT_STRING_t	 synchronousSystemTime;
			BIT_STRING_t	 asynchronousSystemTime;
		} choice;
		
		/* Context for parsing across buffer boundaries */
		asn_struct_ctx_t _asn_ctx;
	} cdma_SystemTime;
	
	/* Context for parsing across buffer boundaries */
	asn_struct_ctx_t _asn_ctx;
} SystemTimeInfoCDMA2000_t;

/* Implementation */
extern asn_TYPE_descriptor_t asn_DEF_SystemTimeInfoCDMA2000;

#ifdef __cplusplus
}
#endif

#endif	/* _SystemTimeInfoCDMA2000_H_ */
#include <asn_internal.h>
