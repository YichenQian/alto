/*
 * Generated by asn1c-0.9.22 (http://lionet.info/asn1c)
 * From ASN.1 module "EUTRA-RRC-Definitions"
 * 	found in "/home/liushifu/openair4G/openair2/RRC/LITE/MESSAGES/asn1c/ASN1_files/EUTRA-RRC-Definitions.asn"
 * 	`asn1c -gen-PER -fcompound-names -fnative-types`
 */

#ifndef	_SupportedBandListUTRA_TDD768_H_
#define	_SupportedBandListUTRA_TDD768_H_


#include <asn_application.h>

/* Including external dependencies */
#include "SupportedBandUTRA-TDD768.h"
#include <asn_SEQUENCE_OF.h>
#include <constr_SEQUENCE_OF.h>

#ifdef __cplusplus
extern "C" {
#endif

/* SupportedBandListUTRA-TDD768 */
typedef struct SupportedBandListUTRA_TDD768 {
	A_SEQUENCE_OF(SupportedBandUTRA_TDD768_t) list;
	
	/* Context for parsing across buffer boundaries */
	asn_struct_ctx_t _asn_ctx;
} SupportedBandListUTRA_TDD768_t;

/* Implementation */
extern asn_TYPE_descriptor_t asn_DEF_SupportedBandListUTRA_TDD768;

#ifdef __cplusplus
}
#endif

#endif	/* _SupportedBandListUTRA_TDD768_H_ */
#include <asn_internal.h>
