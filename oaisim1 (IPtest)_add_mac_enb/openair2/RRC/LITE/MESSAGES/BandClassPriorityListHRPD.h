/*
 * Generated by asn1c-0.9.22 (http://lionet.info/asn1c)
 * From ASN.1 module "EUTRA-RRC-Definitions"
 * 	found in "/home/liushifu/openair4G/openair2/RRC/LITE/MESSAGES/asn1c/ASN1_files/EUTRA-RRC-Definitions.asn"
 * 	`asn1c -gen-PER -fcompound-names -fnative-types`
 */

#ifndef	_BandClassPriorityListHRPD_H_
#define	_BandClassPriorityListHRPD_H_


#include <asn_application.h>

/* Including external dependencies */
#include <asn_SEQUENCE_OF.h>
#include <constr_SEQUENCE_OF.h>

#ifdef __cplusplus
extern "C" {
#endif

/* Forward declarations */
struct BandClassPriorityHRPD;

/* BandClassPriorityListHRPD */
typedef struct BandClassPriorityListHRPD {
	A_SEQUENCE_OF(struct BandClassPriorityHRPD) list;
	
	/* Context for parsing across buffer boundaries */
	asn_struct_ctx_t _asn_ctx;
} BandClassPriorityListHRPD_t;

/* Implementation */
extern asn_TYPE_descriptor_t asn_DEF_BandClassPriorityListHRPD;

#ifdef __cplusplus
}
#endif

/* Referred external types */
#include "BandClassPriorityHRPD.h"

#endif	/* _BandClassPriorityListHRPD_H_ */
#include <asn_internal.h>
