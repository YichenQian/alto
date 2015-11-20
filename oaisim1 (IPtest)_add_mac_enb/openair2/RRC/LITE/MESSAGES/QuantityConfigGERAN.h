/*
 * Generated by asn1c-0.9.22 (http://lionet.info/asn1c)
 * From ASN.1 module "EUTRA-RRC-Definitions"
 * 	found in "/home/liushifu/openair4G/openair2/RRC/LITE/MESSAGES/asn1c/ASN1_files/EUTRA-RRC-Definitions.asn"
 * 	`asn1c -gen-PER -fcompound-names -fnative-types`
 */

#ifndef	_QuantityConfigGERAN_H_
#define	_QuantityConfigGERAN_H_


#include <asn_application.h>

/* Including external dependencies */
#include <NativeEnumerated.h>
#include "FilterCoefficient.h"
#include <constr_SEQUENCE.h>

#ifdef __cplusplus
extern "C" {
#endif

/* Dependencies */
typedef enum QuantityConfigGERAN__measQuantityGERAN {
	QuantityConfigGERAN__measQuantityGERAN_rssi	= 0
} e_QuantityConfigGERAN__measQuantityGERAN;

/* QuantityConfigGERAN */
typedef struct QuantityConfigGERAN {
	long	 measQuantityGERAN;
	FilterCoefficient_t	*filterCoefficient	/* DEFAULT 2 */;
	
	/* Context for parsing across buffer boundaries */
	asn_struct_ctx_t _asn_ctx;
} QuantityConfigGERAN_t;

/* Implementation */
/* extern asn_TYPE_descriptor_t asn_DEF_measQuantityGERAN_2;	// (Use -fall-defs-global to expose) */
extern asn_TYPE_descriptor_t asn_DEF_QuantityConfigGERAN;

#ifdef __cplusplus
}
#endif

#endif	/* _QuantityConfigGERAN_H_ */
#include <asn_internal.h>
