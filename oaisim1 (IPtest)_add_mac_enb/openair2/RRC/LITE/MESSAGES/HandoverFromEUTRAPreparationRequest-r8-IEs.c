/*
 * Generated by asn1c-0.9.22 (http://lionet.info/asn1c)
 * From ASN.1 module "EUTRA-RRC-Definitions"
 * 	found in "/home/liushifu/openair4G/openair2/RRC/LITE/MESSAGES/asn1c/ASN1_files/EUTRA-RRC-Definitions.asn"
 * 	`asn1c -gen-PER -fcompound-names -fnative-types`
 */

#include "HandoverFromEUTRAPreparationRequest-r8-IEs.h"

static ber_tlv_tag_t asn_DEF_nonCriticalExtension_tags_5[] = {
	(ASN_TAG_CLASS_CONTEXT | (3 << 2)),
	(ASN_TAG_CLASS_UNIVERSAL | (16 << 2))
};
static asn_SEQUENCE_specifics_t asn_SPC_nonCriticalExtension_specs_5 = {
	sizeof(struct HandoverFromEUTRAPreparationRequest_r8_IEs__nonCriticalExtension),
	offsetof(struct HandoverFromEUTRAPreparationRequest_r8_IEs__nonCriticalExtension, _asn_ctx),
	0,	/* No top level tags */
	0,	/* No tags in the map */
	0, 0, 0,	/* Optional elements (not needed) */
	-1,	/* Start extensions */
	-1	/* Stop extensions */
};
static /* Use -fall-defs-global to expose */
asn_TYPE_descriptor_t asn_DEF_nonCriticalExtension_5 = {
	"nonCriticalExtension",
	"nonCriticalExtension",
	SEQUENCE_free,
	SEQUENCE_print,
	SEQUENCE_constraint,
	SEQUENCE_decode_ber,
	SEQUENCE_encode_der,
	SEQUENCE_decode_xer,
	SEQUENCE_encode_xer,
	SEQUENCE_decode_uper,
	SEQUENCE_encode_uper,
	0,	/* Use generic outmost tag fetcher */
	asn_DEF_nonCriticalExtension_tags_5,
	sizeof(asn_DEF_nonCriticalExtension_tags_5)
		/sizeof(asn_DEF_nonCriticalExtension_tags_5[0]) - 1, /* 1 */
	asn_DEF_nonCriticalExtension_tags_5,	/* Same as above */
	sizeof(asn_DEF_nonCriticalExtension_tags_5)
		/sizeof(asn_DEF_nonCriticalExtension_tags_5[0]), /* 2 */
	0,	/* No PER visible constraints */
	0, 0,	/* No members */
	&asn_SPC_nonCriticalExtension_specs_5	/* Additional specs */
};

static asn_TYPE_member_t asn_MBR_HandoverFromEUTRAPreparationRequest_r8_IEs_1[] = {
	{ ATF_NOFLAGS, 0, offsetof(struct HandoverFromEUTRAPreparationRequest_r8_IEs, cdma2000_Type),
		(ASN_TAG_CLASS_CONTEXT | (0 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_CDMA2000_Type,
		0,	/* Defer constraints checking to the member type */
		0,	/* No PER visible constraints */
		0,
		"cdma2000-Type"
		},
	{ ATF_POINTER, 3, offsetof(struct HandoverFromEUTRAPreparationRequest_r8_IEs, rand),
		(ASN_TAG_CLASS_CONTEXT | (1 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_RAND_CDMA2000,
		0,	/* Defer constraints checking to the member type */
		0,	/* No PER visible constraints */
		0,
		"rand"
		},
	{ ATF_POINTER, 2, offsetof(struct HandoverFromEUTRAPreparationRequest_r8_IEs, mobilityParameters),
		(ASN_TAG_CLASS_CONTEXT | (2 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_MobilityParametersCDMA2000,
		0,	/* Defer constraints checking to the member type */
		0,	/* No PER visible constraints */
		0,
		"mobilityParameters"
		},
	{ ATF_POINTER, 1, offsetof(struct HandoverFromEUTRAPreparationRequest_r8_IEs, nonCriticalExtension),
		(ASN_TAG_CLASS_CONTEXT | (3 << 2)),
		0,
		&asn_DEF_nonCriticalExtension_5,
		0,	/* Defer constraints checking to the member type */
		0,	/* No PER visible constraints */
		0,
		"nonCriticalExtension"
		},
};
static int asn_MAP_HandoverFromEUTRAPreparationRequest_r8_IEs_oms_1[] = { 1, 2, 3 };
static ber_tlv_tag_t asn_DEF_HandoverFromEUTRAPreparationRequest_r8_IEs_tags_1[] = {
	(ASN_TAG_CLASS_UNIVERSAL | (16 << 2))
};
static asn_TYPE_tag2member_t asn_MAP_HandoverFromEUTRAPreparationRequest_r8_IEs_tag2el_1[] = {
    { (ASN_TAG_CLASS_CONTEXT | (0 << 2)), 0, 0, 0 }, /* cdma2000-Type at 220 */
    { (ASN_TAG_CLASS_CONTEXT | (1 << 2)), 1, 0, 0 }, /* rand at 221 */
    { (ASN_TAG_CLASS_CONTEXT | (2 << 2)), 2, 0, 0 }, /* mobilityParameters at 222 */
    { (ASN_TAG_CLASS_CONTEXT | (3 << 2)), 3, 0, 0 } /* nonCriticalExtension at 223 */
};
static asn_SEQUENCE_specifics_t asn_SPC_HandoverFromEUTRAPreparationRequest_r8_IEs_specs_1 = {
	sizeof(struct HandoverFromEUTRAPreparationRequest_r8_IEs),
	offsetof(struct HandoverFromEUTRAPreparationRequest_r8_IEs, _asn_ctx),
	asn_MAP_HandoverFromEUTRAPreparationRequest_r8_IEs_tag2el_1,
	4,	/* Count of tags in the map */
	asn_MAP_HandoverFromEUTRAPreparationRequest_r8_IEs_oms_1,	/* Optional members */
	3, 0,	/* Root/Additions */
	-1,	/* Start extensions */
	-1	/* Stop extensions */
};
asn_TYPE_descriptor_t asn_DEF_HandoverFromEUTRAPreparationRequest_r8_IEs = {
	"HandoverFromEUTRAPreparationRequest-r8-IEs",
	"HandoverFromEUTRAPreparationRequest-r8-IEs",
	SEQUENCE_free,
	SEQUENCE_print,
	SEQUENCE_constraint,
	SEQUENCE_decode_ber,
	SEQUENCE_encode_der,
	SEQUENCE_decode_xer,
	SEQUENCE_encode_xer,
	SEQUENCE_decode_uper,
	SEQUENCE_encode_uper,
	0,	/* Use generic outmost tag fetcher */
	asn_DEF_HandoverFromEUTRAPreparationRequest_r8_IEs_tags_1,
	sizeof(asn_DEF_HandoverFromEUTRAPreparationRequest_r8_IEs_tags_1)
		/sizeof(asn_DEF_HandoverFromEUTRAPreparationRequest_r8_IEs_tags_1[0]), /* 1 */
	asn_DEF_HandoverFromEUTRAPreparationRequest_r8_IEs_tags_1,	/* Same as above */
	sizeof(asn_DEF_HandoverFromEUTRAPreparationRequest_r8_IEs_tags_1)
		/sizeof(asn_DEF_HandoverFromEUTRAPreparationRequest_r8_IEs_tags_1[0]), /* 1 */
	0,	/* No PER visible constraints */
	asn_MBR_HandoverFromEUTRAPreparationRequest_r8_IEs_1,
	4,	/* Elements count */
	&asn_SPC_HandoverFromEUTRAPreparationRequest_r8_IEs_specs_1	/* Additional specs */
};

