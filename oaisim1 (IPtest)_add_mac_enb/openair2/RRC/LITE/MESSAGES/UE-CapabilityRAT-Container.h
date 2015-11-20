/*
 * Generated by asn1c-0.9.22 (http://lionet.info/asn1c)
 * From ASN.1 module "EUTRA-RRC-Definitions"
 * 	found in "/home/liushifu/openair4G/openair2/RRC/LITE/MESSAGES/asn1c/ASN1_files/EUTRA-RRC-Definitions.asn"
 * 	`asn1c -gen-PER -fcompound-names -fnative-types`
 */

#ifndef	_UE_CapabilityRAT_Container_H_
#define	_UE_CapabilityRAT_Container_H_


#include <asn_application.h>

/* Including external dependencies */
#include "RAT-Type.h"
#include <OCTET_STRING.h>
#include <constr_SEQUENCE.h>

#ifdef __cplusplus
extern "C" {
#endif

/* UE-CapabilityRAT-Container */
typedef struct UE_CapabilityRAT_Container {
	RAT_Type_t	 rat_Type;
	OCTET_STRING_t	 ueCapabilityRAT_Container;
	
	/* Context for parsing across buffer boundaries */
	asn_struct_ctx_t _asn_ctx;
} UE_CapabilityRAT_Container_t;

/* Implementation */
extern asn_TYPE_descriptor_t asn_DEF_UE_CapabilityRAT_Container;

#ifdef __cplusplus
}
#endif

#endif	/* _UE_CapabilityRAT_Container_H_ */
#include <asn_internal.h>
