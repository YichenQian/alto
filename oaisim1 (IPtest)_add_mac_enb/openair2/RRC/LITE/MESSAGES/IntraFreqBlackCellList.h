/*
 * Generated by asn1c-0.9.22 (http://lionet.info/asn1c)
 * From ASN.1 module "EUTRA-RRC-Definitions"
 * 	found in "/home/liushifu/openair4G/openair2/RRC/LITE/MESSAGES/asn1c/ASN1_files/EUTRA-RRC-Definitions.asn"
 * 	`asn1c -gen-PER -fcompound-names -fnative-types`
 */

#ifndef	_IntraFreqBlackCellList_H_
#define	_IntraFreqBlackCellList_H_


#include <asn_application.h>

/* Including external dependencies */
#include <asn_SEQUENCE_OF.h>
#include <constr_SEQUENCE_OF.h>

#ifdef __cplusplus
extern "C" {
#endif

/* Forward declarations */
struct PhysCellIdRange;

/* IntraFreqBlackCellList */
typedef struct IntraFreqBlackCellList {
	A_SEQUENCE_OF(struct PhysCellIdRange) list;
	
	/* Context for parsing across buffer boundaries */
	asn_struct_ctx_t _asn_ctx;
} IntraFreqBlackCellList_t;

/* Implementation */
extern asn_TYPE_descriptor_t asn_DEF_IntraFreqBlackCellList;

#ifdef __cplusplus
}
#endif

/* Referred external types */
#include "PhysCellIdRange.h"

#endif	/* _IntraFreqBlackCellList_H_ */
#include <asn_internal.h>
