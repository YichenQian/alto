/*
 * Generated by asn1c-0.9.22 (http://lionet.info/asn1c)
 * From ASN.1 module "EUTRA-RRC-Definitions"
 * 	found in "/home/liushifu/openair4G/openair2/RRC/LITE/MESSAGES/asn1c/ASN1_files/EUTRA-RRC-Definitions.asn"
 * 	`asn1c -gen-PER -fcompound-names -fnative-types`
 */

#include "PUSCH-ConfigCommon.h"

static int
hoppingMode_4_constraint(asn_TYPE_descriptor_t *td, const void *sptr,
			asn_app_constraint_failed_f *ctfailcb, void *app_key) {
	/* Replace with underlying type checker */
	td->check_constraints = asn_DEF_NativeEnumerated.check_constraints;
	return td->check_constraints(td, sptr, ctfailcb, app_key);
}

/*
 * This type is implemented using NativeEnumerated,
 * so here we adjust the DEF accordingly.
 */
static void
hoppingMode_4_inherit_TYPE_descriptor(asn_TYPE_descriptor_t *td) {
	td->free_struct    = asn_DEF_NativeEnumerated.free_struct;
	td->print_struct   = asn_DEF_NativeEnumerated.print_struct;
	td->ber_decoder    = asn_DEF_NativeEnumerated.ber_decoder;
	td->der_encoder    = asn_DEF_NativeEnumerated.der_encoder;
	td->xer_decoder    = asn_DEF_NativeEnumerated.xer_decoder;
	td->xer_encoder    = asn_DEF_NativeEnumerated.xer_encoder;
	td->uper_decoder   = asn_DEF_NativeEnumerated.uper_decoder;
	td->uper_encoder   = asn_DEF_NativeEnumerated.uper_encoder;
	if(!td->per_constraints)
		td->per_constraints = asn_DEF_NativeEnumerated.per_constraints;
	td->elements       = asn_DEF_NativeEnumerated.elements;
	td->elements_count = asn_DEF_NativeEnumerated.elements_count;
     /* td->specifics      = asn_DEF_NativeEnumerated.specifics;	// Defined explicitly */
}

static void
hoppingMode_4_free(asn_TYPE_descriptor_t *td,
		void *struct_ptr, int contents_only) {
	hoppingMode_4_inherit_TYPE_descriptor(td);
	td->free_struct(td, struct_ptr, contents_only);
}

static int
hoppingMode_4_print(asn_TYPE_descriptor_t *td, const void *struct_ptr,
		int ilevel, asn_app_consume_bytes_f *cb, void *app_key) {
	hoppingMode_4_inherit_TYPE_descriptor(td);
	return td->print_struct(td, struct_ptr, ilevel, cb, app_key);
}

static asn_dec_rval_t
hoppingMode_4_decode_ber(asn_codec_ctx_t *opt_codec_ctx, asn_TYPE_descriptor_t *td,
		void **structure, const void *bufptr, size_t size, int tag_mode) {
	hoppingMode_4_inherit_TYPE_descriptor(td);
	return td->ber_decoder(opt_codec_ctx, td, structure, bufptr, size, tag_mode);
}

static asn_enc_rval_t
hoppingMode_4_encode_der(asn_TYPE_descriptor_t *td,
		void *structure, int tag_mode, ber_tlv_tag_t tag,
		asn_app_consume_bytes_f *cb, void *app_key) {
	hoppingMode_4_inherit_TYPE_descriptor(td);
	return td->der_encoder(td, structure, tag_mode, tag, cb, app_key);
}

static asn_dec_rval_t
hoppingMode_4_decode_xer(asn_codec_ctx_t *opt_codec_ctx, asn_TYPE_descriptor_t *td,
		void **structure, const char *opt_mname, const void *bufptr, size_t size) {
	hoppingMode_4_inherit_TYPE_descriptor(td);
	return td->xer_decoder(opt_codec_ctx, td, structure, opt_mname, bufptr, size);
}

static asn_enc_rval_t
hoppingMode_4_encode_xer(asn_TYPE_descriptor_t *td, void *structure,
		int ilevel, enum xer_encoder_flags_e flags,
		asn_app_consume_bytes_f *cb, void *app_key) {
	hoppingMode_4_inherit_TYPE_descriptor(td);
	return td->xer_encoder(td, structure, ilevel, flags, cb, app_key);
}

static asn_dec_rval_t
hoppingMode_4_decode_uper(asn_codec_ctx_t *opt_codec_ctx, asn_TYPE_descriptor_t *td,
		asn_per_constraints_t *constraints, void **structure, asn_per_data_t *per_data) {
	hoppingMode_4_inherit_TYPE_descriptor(td);
	return td->uper_decoder(opt_codec_ctx, td, constraints, structure, per_data);
}

static asn_enc_rval_t
hoppingMode_4_encode_uper(asn_TYPE_descriptor_t *td,
		asn_per_constraints_t *constraints,
		void *structure, asn_per_outp_t *per_out) {
	hoppingMode_4_inherit_TYPE_descriptor(td);
	return td->uper_encoder(td, constraints, structure, per_out);
}

static int
memb_n_SB_constraint_2(asn_TYPE_descriptor_t *td, const void *sptr,
			asn_app_constraint_failed_f *ctfailcb, void *app_key) {
	long value;
	
	if(!sptr) {
		_ASN_CTFAIL(app_key, td, sptr,
			"%s: value not given (%s:%d)",
			td->name, __FILE__, __LINE__);
		return -1;
	}
	
	value = *(const long *)sptr;
	
	if((value >= 1 && value <= 4)) {
		/* Constraint check succeeded */
		return 0;
	} else {
		_ASN_CTFAIL(app_key, td, sptr,
			"%s: constraint failed (%s:%d)",
			td->name, __FILE__, __LINE__);
		return -1;
	}
}

static int
memb_pusch_HoppingOffset_constraint_2(asn_TYPE_descriptor_t *td, const void *sptr,
			asn_app_constraint_failed_f *ctfailcb, void *app_key) {
	long value;
	
	if(!sptr) {
		_ASN_CTFAIL(app_key, td, sptr,
			"%s: value not given (%s:%d)",
			td->name, __FILE__, __LINE__);
		return -1;
	}
	
	value = *(const long *)sptr;
	
	if((value >= 0 && value <= 98)) {
		/* Constraint check succeeded */
		return 0;
	} else {
		_ASN_CTFAIL(app_key, td, sptr,
			"%s: constraint failed (%s:%d)",
			td->name, __FILE__, __LINE__);
		return -1;
	}
}

static asn_per_constraints_t asn_PER_type_hoppingMode_constr_4 = {
	{ APC_CONSTRAINED,	 1,  1,  0,  1 }	/* (0..1) */,
	{ APC_UNCONSTRAINED,	-1, -1,  0,  0 },
	0, 0	/* No PER value map */
};
static asn_per_constraints_t asn_PER_memb_n_SB_constr_3 = {
	{ APC_CONSTRAINED,	 2,  2,  1,  4 }	/* (1..4) */,
	{ APC_UNCONSTRAINED,	-1, -1,  0,  0 },
	0, 0	/* No PER value map */
};
static asn_per_constraints_t asn_PER_memb_pusch_HoppingOffset_constr_7 = {
	{ APC_CONSTRAINED,	 7,  7,  0,  98 }	/* (0..98) */,
	{ APC_UNCONSTRAINED,	-1, -1,  0,  0 },
	0, 0	/* No PER value map */
};
static asn_INTEGER_enum_map_t asn_MAP_hoppingMode_value2enum_4[] = {
	{ 0,	13,	"interSubFrame" },
	{ 1,	21,	"intraAndInterSubFrame" }
};
static unsigned int asn_MAP_hoppingMode_enum2value_4[] = {
	0,	/* interSubFrame(0) */
	1	/* intraAndInterSubFrame(1) */
};
static asn_INTEGER_specifics_t asn_SPC_hoppingMode_specs_4 = {
	asn_MAP_hoppingMode_value2enum_4,	/* "tag" => N; sorted by tag */
	asn_MAP_hoppingMode_enum2value_4,	/* N => "tag"; sorted by N */
	2,	/* Number of elements in the maps */
	0,	/* Enumeration is not extensible */
	1,	/* Strict enumeration */
	0,	/* Native long size */
	0
};
static ber_tlv_tag_t asn_DEF_hoppingMode_tags_4[] = {
	(ASN_TAG_CLASS_CONTEXT | (1 << 2)),
	(ASN_TAG_CLASS_UNIVERSAL | (10 << 2))
};
static /* Use -fall-defs-global to expose */
asn_TYPE_descriptor_t asn_DEF_hoppingMode_4 = {
	"hoppingMode",
	"hoppingMode",
	hoppingMode_4_free,
	hoppingMode_4_print,
	hoppingMode_4_constraint,
	hoppingMode_4_decode_ber,
	hoppingMode_4_encode_der,
	hoppingMode_4_decode_xer,
	hoppingMode_4_encode_xer,
	hoppingMode_4_decode_uper,
	hoppingMode_4_encode_uper,
	0,	/* Use generic outmost tag fetcher */
	asn_DEF_hoppingMode_tags_4,
	sizeof(asn_DEF_hoppingMode_tags_4)
		/sizeof(asn_DEF_hoppingMode_tags_4[0]) - 1, /* 1 */
	asn_DEF_hoppingMode_tags_4,	/* Same as above */
	sizeof(asn_DEF_hoppingMode_tags_4)
		/sizeof(asn_DEF_hoppingMode_tags_4[0]), /* 2 */
	&asn_PER_type_hoppingMode_constr_4,
	0, 0,	/* Defined elsewhere */
	&asn_SPC_hoppingMode_specs_4	/* Additional specs */
};

static asn_TYPE_member_t asn_MBR_pusch_ConfigBasic_2[] = {
	{ ATF_NOFLAGS, 0, offsetof(struct PUSCH_ConfigCommon__pusch_ConfigBasic, n_SB),
		(ASN_TAG_CLASS_CONTEXT | (0 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_NativeInteger,
		memb_n_SB_constraint_2,
		&asn_PER_memb_n_SB_constr_3,
		0,
		"n-SB"
		},
	{ ATF_NOFLAGS, 0, offsetof(struct PUSCH_ConfigCommon__pusch_ConfigBasic, hoppingMode),
		(ASN_TAG_CLASS_CONTEXT | (1 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_hoppingMode_4,
		0,	/* Defer constraints checking to the member type */
		0,	/* No PER visible constraints */
		0,
		"hoppingMode"
		},
	{ ATF_NOFLAGS, 0, offsetof(struct PUSCH_ConfigCommon__pusch_ConfigBasic, pusch_HoppingOffset),
		(ASN_TAG_CLASS_CONTEXT | (2 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_NativeInteger,
		memb_pusch_HoppingOffset_constraint_2,
		&asn_PER_memb_pusch_HoppingOffset_constr_7,
		0,
		"pusch-HoppingOffset"
		},
	{ ATF_NOFLAGS, 0, offsetof(struct PUSCH_ConfigCommon__pusch_ConfigBasic, enable64QAM),
		(ASN_TAG_CLASS_CONTEXT | (3 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_BOOLEAN,
		0,	/* Defer constraints checking to the member type */
		0,	/* No PER visible constraints */
		0,
		"enable64QAM"
		},
};
static ber_tlv_tag_t asn_DEF_pusch_ConfigBasic_tags_2[] = {
	(ASN_TAG_CLASS_CONTEXT | (0 << 2)),
	(ASN_TAG_CLASS_UNIVERSAL | (16 << 2))
};
static asn_TYPE_tag2member_t asn_MAP_pusch_ConfigBasic_tag2el_2[] = {
    { (ASN_TAG_CLASS_CONTEXT | (0 << 2)), 0, 0, 0 }, /* n-SB at 1343 */
    { (ASN_TAG_CLASS_CONTEXT | (1 << 2)), 1, 0, 0 }, /* hoppingMode at 1344 */
    { (ASN_TAG_CLASS_CONTEXT | (2 << 2)), 2, 0, 0 }, /* pusch-HoppingOffset at 1345 */
    { (ASN_TAG_CLASS_CONTEXT | (3 << 2)), 3, 0, 0 } /* enable64QAM at 1346 */
};
static asn_SEQUENCE_specifics_t asn_SPC_pusch_ConfigBasic_specs_2 = {
	sizeof(struct PUSCH_ConfigCommon__pusch_ConfigBasic),
	offsetof(struct PUSCH_ConfigCommon__pusch_ConfigBasic, _asn_ctx),
	asn_MAP_pusch_ConfigBasic_tag2el_2,
	4,	/* Count of tags in the map */
	0, 0, 0,	/* Optional elements (not needed) */
	-1,	/* Start extensions */
	-1	/* Stop extensions */
};
static /* Use -fall-defs-global to expose */
asn_TYPE_descriptor_t asn_DEF_pusch_ConfigBasic_2 = {
	"pusch-ConfigBasic",
	"pusch-ConfigBasic",
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
	asn_DEF_pusch_ConfigBasic_tags_2,
	sizeof(asn_DEF_pusch_ConfigBasic_tags_2)
		/sizeof(asn_DEF_pusch_ConfigBasic_tags_2[0]) - 1, /* 1 */
	asn_DEF_pusch_ConfigBasic_tags_2,	/* Same as above */
	sizeof(asn_DEF_pusch_ConfigBasic_tags_2)
		/sizeof(asn_DEF_pusch_ConfigBasic_tags_2[0]), /* 2 */
	0,	/* No PER visible constraints */
	asn_MBR_pusch_ConfigBasic_2,
	4,	/* Elements count */
	&asn_SPC_pusch_ConfigBasic_specs_2	/* Additional specs */
};

static asn_TYPE_member_t asn_MBR_PUSCH_ConfigCommon_1[] = {
	{ ATF_NOFLAGS, 0, offsetof(struct PUSCH_ConfigCommon, pusch_ConfigBasic),
		(ASN_TAG_CLASS_CONTEXT | (0 << 2)),
		0,
		&asn_DEF_pusch_ConfigBasic_2,
		0,	/* Defer constraints checking to the member type */
		0,	/* No PER visible constraints */
		0,
		"pusch-ConfigBasic"
		},
	{ ATF_NOFLAGS, 0, offsetof(struct PUSCH_ConfigCommon, ul_ReferenceSignalsPUSCH),
		(ASN_TAG_CLASS_CONTEXT | (1 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_UL_ReferenceSignalsPUSCH,
		0,	/* Defer constraints checking to the member type */
		0,	/* No PER visible constraints */
		0,
		"ul-ReferenceSignalsPUSCH"
		},
};
static ber_tlv_tag_t asn_DEF_PUSCH_ConfigCommon_tags_1[] = {
	(ASN_TAG_CLASS_UNIVERSAL | (16 << 2))
};
static asn_TYPE_tag2member_t asn_MAP_PUSCH_ConfigCommon_tag2el_1[] = {
    { (ASN_TAG_CLASS_CONTEXT | (0 << 2)), 0, 0, 0 }, /* pusch-ConfigBasic at 1343 */
    { (ASN_TAG_CLASS_CONTEXT | (1 << 2)), 1, 0, 0 } /* ul-ReferenceSignalsPUSCH at 1349 */
};
static asn_SEQUENCE_specifics_t asn_SPC_PUSCH_ConfigCommon_specs_1 = {
	sizeof(struct PUSCH_ConfigCommon),
	offsetof(struct PUSCH_ConfigCommon, _asn_ctx),
	asn_MAP_PUSCH_ConfigCommon_tag2el_1,
	2,	/* Count of tags in the map */
	0, 0, 0,	/* Optional elements (not needed) */
	-1,	/* Start extensions */
	-1	/* Stop extensions */
};
asn_TYPE_descriptor_t asn_DEF_PUSCH_ConfigCommon = {
	"PUSCH-ConfigCommon",
	"PUSCH-ConfigCommon",
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
	asn_DEF_PUSCH_ConfigCommon_tags_1,
	sizeof(asn_DEF_PUSCH_ConfigCommon_tags_1)
		/sizeof(asn_DEF_PUSCH_ConfigCommon_tags_1[0]), /* 1 */
	asn_DEF_PUSCH_ConfigCommon_tags_1,	/* Same as above */
	sizeof(asn_DEF_PUSCH_ConfigCommon_tags_1)
		/sizeof(asn_DEF_PUSCH_ConfigCommon_tags_1[0]), /* 1 */
	0,	/* No PER visible constraints */
	asn_MBR_PUSCH_ConfigCommon_1,
	2,	/* Elements count */
	&asn_SPC_PUSCH_ConfigCommon_specs_1	/* Additional specs */
};

