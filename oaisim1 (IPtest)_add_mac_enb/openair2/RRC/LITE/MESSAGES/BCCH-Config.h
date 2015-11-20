/*
 * Generated by asn1c-0.9.22 (http://lionet.info/asn1c)
 * From ASN.1 module "EUTRA-RRC-Definitions"
 * 	found in "/home/liushifu/openair4G/openair2/RRC/LITE/MESSAGES/asn1c/ASN1_files/EUTRA-RRC-Definitions.asn"
 * 	`asn1c -gen-PER -fcompound-names -fnative-types`
 */

#ifndef	_BCCH_Config_H_
#define	_BCCH_Config_H_


#include <asn_application.h>

/* Including external dependencies */
#include <NativeEnumerated.h>
#include <constr_SEQUENCE.h>

#ifdef __cplusplus
extern "C" {
#endif

/* Dependencies */
typedef enum BCCH_Config__modificationPeriodCoeff {
	BCCH_Config__modificationPeriodCoeff_n2	= 0,
	BCCH_Config__modificationPeriodCoeff_n4	= 1,
	BCCH_Config__modificationPeriodCoeff_n8	= 2,
	BCCH_Config__modificationPeriodCoeff_n16	= 3
} e_BCCH_Config__modificationPeriodCoeff;

/* BCCH-Config */
typedef struct BCCH_Config {
	long	 modificationPeriodCoeff;
	
	/* Context for parsing across buffer boundaries */
	asn_struct_ctx_t _asn_ctx;
} BCCH_Config_t;

/* Implementation */
/* extern asn_TYPE_descriptor_t asn_DEF_modificationPeriodCoeff_2;	// (Use -fall-defs-global to expose) */
extern asn_TYPE_descriptor_t asn_DEF_BCCH_Config;

#ifdef __cplusplus
}
#endif

#endif	/* _BCCH_Config_H_ */
#include <asn_internal.h>
