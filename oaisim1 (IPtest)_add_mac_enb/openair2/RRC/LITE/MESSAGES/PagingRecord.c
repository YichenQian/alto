/*
 * Generated by asn1c-0.9.22 (http://lionet.info/asn1c)
 * From ASN.1 module "EUTRA-RRC-Definitions"
 * 	found in "/home/liushifu/openair4G/openair2/RRC/LITE/MESSAGES/asn1c/ASN1_files/EUTRA-RRC-Definitions.asn"
 * 	`asn1c -gen-PER -fcompound-names -fnative-types`
 */

#include "PagingRecord.h"

static int
cn_Domain_3_constraint(asn_TYPE_descriptor_t *td, const void *sptr,
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
cn_Domain_3_inherit_TYPE_descriptor(asn_TYPE_descriptor_t *td) {
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
cn_Domain_3_free(asn_TYPE_descriptor_t *td,
		void *struct_ptr, int contents_only) {
	cn_Domain_3_inherit_TYPE_descriptor(td);
	td->free_struct(td, struct_ptr, contents_only);
}

static int
cn_Domain_3_print(asn_TYPE_descriptor_t *td, const void *struct_ptr,
		int ilevel, asn_app_consume_bytes_f *cb, void *app_key) {
	cn_Domain_3_inherit_TYPE_descriptor(td);
	return td->print_struct(td, struct_ptr, ilevel, cb, app_key);
}

static asn_dec_rval_t
cn_Domain_3_decode_ber(asn_codec_ctx_t *opt_codec_ctx, asn_TYPE_descriptor_t *td,
		void **structure, const void *bufptr, size_t size, int tag_mode) {
	cn_Domain_3_inherit_TYPE_descriptor(td);
	return td->ber_decoder(opt_codec_ctx, td, structure, bufptr, size, tag_mode);
}

static asn_enc_rval_t
cn_Domain_3_encode_der(asn_TYPE_descriptor_t *td,
		void *structure, int tag_mode, ber_tlv_tag_t tag,
		asn_app_consume_bytes_f *cb, void *app_key) {
	cn_Domain_3_inherit_TYPE_descriptor(td);
	return td->der_encoder(td, structure, tag_mode, tag, cb, app_key);
}

static asn_dec_rval_t
cn_Domain_3_decode_xer(asn_codec_ctx_t *opt_codec_ctx, asn_TYPE_descriptor_t *td,
		void **structure, const char *opt_mname, const void *bufptr, size_t size) {
	cn_Domain_3_inherit_TYPE_descriptor(td);
	return td->xer_decoder(opt_codec_ctx, td, structure, opt_mname, bufptr, size);
}

static asn_enc_rval_t
cn_Domain_3_encode_xer(asn_TYPE_descriptor_t *td, void *structure,
		int ilevel, enum xer_encoder_flags_e flags,
		asn_app_consume_bytes_f *cb, void *app_key) {
	cn_Domain_3_inherit_TYPE_descriptor(td);
	return td->xer_encoder(td, structure, ilevel, flags, cb, app_key);
}

static asn_dec_rval_t
cn_Domain_3_decode_uper(asn_codec_ctx_t *opt_codec_ctx, asn_TYPE_descriptor_t *td,
		asn_per_constraints_t *constraints, void **structure, asn_per_data_t *per_data) {
	cn_Domain_3_inherit_TYPE_descriptor(td);
	return td->uper_decoder(opt_codec_ctx, td, constraints, structure, per_data);
}

static asn_enc_rval_t
cn_Domain_3_encode_uper(asn_TYPE_descriptor_t *td,
		asn_per_constraints_t *constraints,
		void *structure, asn_per_outp_t *per_out) {
	cn_Domain_3_inherit_TYPE_descriptor(td);
	return td->uper_encoder(td, constraints, structure, per_out);
}

static asn_per_constraints_t asn_PER_type_cn_Domain_constr_3 = {
	{ APC_CONSTRAINED,	 1,  1,  0,  1 }	/* (0..1) */,
	{ APC_UNCONSTRAINED,	-1, -1,  0,  0 },
	0, 0	/* No PER value map */
};
static asn_INTEGER_enum_map_t asn_MAP_cn_Domain_value2enum_3[] = {
	{ 0,	2,	"ps" },
	{ 1,	2,	"cs" }
};
static unsigned int asn_MAP_cn_Domain_enum2value_3[] = {
	1,	/* cs(1) */
	0	/* ps(0) */
};
static asn_INTEGER_specifics_t asn_SPC_cn_Domain_specs_3 = {
	asn_MAP_cn_Domain_value2enum_3,	/* "tag" => N; sorted by tag */
	asn_MAP_cn_Domain_enum2value_3,	/* N => "tag"; sorted by N */
	2,	/* Number of elements in the maps */
	0,	/* Enumeration is not extensible */
	1,	/* Strict enumeration */
	0,	/* Native long size */
	0
};
static ber_tlv_tag_t asn_DEF_cn_Domain_tags_3[] = {
	(ASN_TAG_CLASS_CONTEXT | (1 << 2)),
	(ASN_TAG_CLASS_UNIVERSAL | (10 << 2))
};
static /* Use -fall-defs-global to expose */
asn_TYPE_descriptor_t asn_DEF_cn_Domain_3 = {
	"cn-Domain",
	"cn-Domain",
	cn_Domain_3_free,
	cn_Domain_3_print,
	cn_Domain_3_constraint,
	cn_Domain_3_decode_ber,
	cn_Domain_3_encode_der,
	cn_Domain_3_decode_xer,
	cn_Domain_3_encode_xer,
	cn_Domain_3_decode_uper,
	cn_Domain_3_encode_uper,
	0,	/* Use generic outmost tag fetcher */
	asn_DEF_cn_Domain_tags_3,
	sizeof(asn_DEF_cn_Domain_tags_3)
		/sizeof(asn_DEF_cn_Domain_tags_3[0]) - 1, /* 1 */
	asn_DEF_cn_Domain_tags_3,	/* Same as above */
	sizeof(asn_DEF_cn_Domain_tags_3)
		/sizeof(asn_DEF_cn_Domain_tags_3[0]), /* 2 */
	&asn_PER_type_cn_Domain_constr_3,
	0, 0,	/* Defined elsewhere */
	&asn_SPC_cn_Domain_specs_3	/* Additional specs */
};

static asn_TYPE_member_t asn_MBR_PagingRecord_1[] = {
	{ ATF_NOFLAGS, 0, offsetof(struct PagingRecord, ue_Identity),
		(ASN_TAG_CLASS_CONTEXT | (0 << 2)),
		+1,	/* EXPLICIT tag at current level */
		&asn_DEF_PagingUE_Identity,
		0,	/* Defer constraints checking to the member type */
		0,	/* No PER visible constraints */
		0,
		"ue-Identity"
		},
	{ ATF_NOFLAGS, 0, offsetof(struct PagingRecord, cn_Domain),
		(ASN_TAG_CLASS_CONTEXT | (1 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_cn_Domain_3,
		0,	/* Defer constraints checking to the member type */
		0,	/* No PER visible constraints */
		0,
		"cn-Domain"
		},
};
static ber_tlv_tag_t asn_DEF_PagingRecord_tags_1[] = {
	(ASN_TAG_CLASS_UNIVERSAL | (16 << 2))
};
static asn_TYPE_tag2member_t asn_MAP_PagingRecord_tag2el_1[] = {
    { (ASN_TAG_CLASS_CONTEXT | (0 << 2)), 0, 0, 0 }, /* ue-Identity at 318 */
    { (ASN_TAG_CLASS_CONTEXT | (1 << 2)), 1, 0, 0 } /* cn-Domain at 319 */
};
static asn_SEQUENCE_specifics_t asn_SPC_PagingRecord_specs_1 = {
	sizeof(struct PagingRecord),
	offsetof(struct PagingRecord, _asn_ctx),
	asn_MAP_PagingRecord_tag2el_1,
	2,	/* Count of tags in the map */
	0, 0, 0,	/* Optional elements (not needed) */
	1,	/* Start extensions */
	3	/* Stop extensions */
};
asn_TYPE_descriptor_t asn_DEF_PagingRecord = {
	"PagingRecord",
	"PagingRecord",
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
	asn_DEF_PagingRecord_tags_1,
	sizeof(asn_DEF_PagingRecord_tags_1)
		/sizeof(asn_DEF_PagingRecord_tags_1[0]), /* 1 */
	asn_DEF_PagingRecord_tags_1,	/* Same as above */
	sizeof(asn_DEF_PagingRecord_tags_1)
		/sizeof(asn_DEF_PagingRecord_tags_1[0]), /* 1 */
	0,	/* No PER visible constraints */
	asn_MBR_PagingRecord_1,
	2,	/* Elements count */
	&asn_SPC_PagingRecord_specs_1	/* Additional specs */
};

