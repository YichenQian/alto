/*
 * Generated by asn1c-0.9.22 (http://lionet.info/asn1c)
 * From ASN.1 module "EUTRA-RRC-Definitions"
 * 	found in "/home/liushifu/openair4G/openair2/RRC/LITE/MESSAGES/asn1c/ASN1_files/EUTRA-RRC-Definitions.asn"
 * 	`asn1c -gen-PER -fcompound-names -fnative-types`
 */

#ifndef	_MeasurementReport_r8_IEs_H_
#define	_MeasurementReport_r8_IEs_H_


#include <asn_application.h>

/* Including external dependencies */
#include "MeasResults.h"
#include <constr_SEQUENCE.h>

#ifdef __cplusplus
extern "C" {
#endif

/* MeasurementReport-r8-IEs */
typedef struct MeasurementReport_r8_IEs {
	MeasResults_t	 measResults;
	struct MeasurementReport_r8_IEs__nonCriticalExtension {
		
		/* Context for parsing across buffer boundaries */
		asn_struct_ctx_t _asn_ctx;
	} *nonCriticalExtension;
	
	/* Context for parsing across buffer boundaries */
	asn_struct_ctx_t _asn_ctx;
} MeasurementReport_r8_IEs_t;

/* Implementation */
extern asn_TYPE_descriptor_t asn_DEF_MeasurementReport_r8_IEs;

#ifdef __cplusplus
}
#endif

#endif	/* _MeasurementReport_r8_IEs_H_ */
#include <asn_internal.h>
