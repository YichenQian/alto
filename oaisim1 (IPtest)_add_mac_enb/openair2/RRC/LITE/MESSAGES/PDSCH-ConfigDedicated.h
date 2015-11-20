/*
 * Generated by asn1c-0.9.22 (http://lionet.info/asn1c)
 * From ASN.1 module "EUTRA-RRC-Definitions"
 * 	found in "/home/liushifu/openair4G/openair2/RRC/LITE/MESSAGES/asn1c/ASN1_files/EUTRA-RRC-Definitions.asn"
 * 	`asn1c -gen-PER -fcompound-names -fnative-types`
 */

#ifndef	_PDSCH_ConfigDedicated_H_
#define	_PDSCH_ConfigDedicated_H_


#include <asn_application.h>

/* Including external dependencies */
#include <NativeEnumerated.h>
#include <constr_SEQUENCE.h>

#ifdef __cplusplus
extern "C" {
#endif

/* Dependencies */
typedef enum PDSCH_ConfigDedicated__p_a {
	PDSCH_ConfigDedicated__p_a_dB_6	= 0,
	PDSCH_ConfigDedicated__p_a_dB_4dot77	= 1,
	PDSCH_ConfigDedicated__p_a_dB_3	= 2,
	PDSCH_ConfigDedicated__p_a_dB_1dot77	= 3,
	PDSCH_ConfigDedicated__p_a_dB0	= 4,
	PDSCH_ConfigDedicated__p_a_dB1	= 5,
	PDSCH_ConfigDedicated__p_a_dB2	= 6,
	PDSCH_ConfigDedicated__p_a_dB3	= 7
} e_PDSCH_ConfigDedicated__p_a;

/* PDSCH-ConfigDedicated */
typedef struct PDSCH_ConfigDedicated {
	long	 p_a;
	
	/* Context for parsing across buffer boundaries */
	asn_struct_ctx_t _asn_ctx;
} PDSCH_ConfigDedicated_t;

/* Implementation */
/* extern asn_TYPE_descriptor_t asn_DEF_p_a_2;	// (Use -fall-defs-global to expose) */
extern asn_TYPE_descriptor_t asn_DEF_PDSCH_ConfigDedicated;

#ifdef __cplusplus
}
#endif

#endif	/* _PDSCH_ConfigDedicated_H_ */
#include <asn_internal.h>
