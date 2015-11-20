/*
 * Generated by asn1c-0.9.22 (http://lionet.info/asn1c)
 * From ASN.1 module "EUTRA-RRC-Definitions"
 * 	found in "/home/liushifu/openair4G/openair2/RRC/LITE/MESSAGES/asn1c/ASN1_files/EUTRA-RRC-Definitions.asn"
 * 	`asn1c -gen-PER -fcompound-names -fnative-types`
 */

#include "PDSCH-ConfigDedicated.h"

static int
p_a_2_constraint(asn_TYPE_descriptor_t *td, const void *sptr,
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
p_a_2_inherit_TYPE_descriptor(asn_TYPE_descriptor_t *td) {
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
p_a_2_free(asn_TYPE_descriptor_t *td,
		void *struct_ptr, int contents_only) {
	p_a_2_inherit_TYPE_descriptor(td);
	td->free_struct(td, struct_ptr, contents_only);
}

static int
p_a_2_print(asn_TYPE_descriptor_t *td, const void *struct_ptr,
		int ilevel, asn_app_consume_bytes_f *cb, void *app_key) {
	p_a_2_inherit_TYPE_descriptor(td);
	return td->print_struct(td, struct_ptr, ilevel, cb, app_key);
}

static asn_dec_rval_t
p_a_2_decode_ber(asn_codec_ctx_t *opt_codec_ctx, asn_TYPE_descriptor_t *td,
		void **structure, const void *bufptr, size_t size, int tag_mode) {
	p_a_2_inherit_TYPE_descriptor(td);
	return td->ber_decoder(opt_codec_ctx, td, structure, bufptr, size, tag_mode);
}

static asn_enc_rval_t
p_a_2_encode_der(asn_TYPE_descriptor_t *td,
		void *structure, int tag_mode, ber_tlv_tag_t tag,
		asn_app_consume_bytes_f *cb, void *app_key) {
	p_a_2_inherit_TYPE_descriptor(td);
	return td->der_encoder(td, structure, tag_mode, tag, cb, app_key);
}

static asn_dec_rval_t
p_a_2_decode_xer(asn_codec_ctx_t *opt_codec_ctx, asn_TYPE_descriptor_t *td,
		void **structure, const char *opt_mname, const void *bufptr, size_t size) {
	p_a_2_inherit_TYPE_descriptor(td);
	return td->xer_decoder(opt_codec_ctx, td, structure, opt_mname, bufptr, size);
}

static asn_enc_rval_t
p_a_2_encode_xer(asn_TYPE_descriptor_t *td, void *structure,
		int ilevel, enum xer_encoder_flags_e flags,
		asn_app_consume_bytes_f *cb, void *app_key) {
	p_a_2_inherit_TYPE_descriptor(td);
	return td->xer_encoder(td, structure, ilevel, flags, cb, app_key);
}

static asn_dec_rval_t
p_a_2_decode_uper(asn_codec_ctx_t *opt_codec_ctx, asn_TYPE_descriptor_t *td,
		asn_per_constraints_t *constraints, void **structure, asn_per_data_t *per_data) {
	p_a_2_inherit_TYPE_descriptor(td);
	return td->uper_decoder(opt_codec_ctx, td, constraints, structure, per_data);
}

static asn_enc_rval_t
p_a_2_encode_uper(asn_TYPE_descriptor_t *td,
		asn_per_constraints_t *constraints,
		void *structure, asn_per_outp_t *per_out) {
	p_a_2_inherit_TYPE_descriptor(td);
	return td->uper_encoder(td, constraints, structure, per_out);
}

static asn_per_constraints_t asn_PER_type_p_a_constr_2 = {
	{ APC_CONSTRAINED,	 3,  3,  0,  7 }	/* (0..7) */,
	{ APC_UNCONSTRAINED,	-1, -1,  0,  0 },
	0, 0	/* No PER value map */
};
static asn_INTEGER_enum_map_t asn_MAP_p_a_value2enum_2[] = {
	{ 0,	4,	"dB-6" },
	{ 1,	9,	"dB-4dot77" },
	{ 2,	4,	"dB-3" },
	{ 3,	9,	"dB-1dot77" },
	{ 4,	3,	"dB0" },
	{ 5,	3,	"dB1" },
	{ 6,	3,	"dB2" },
	{ 7,	3,	"dB3" }
};
static unsigned int asn_MAP_p_a_enum2value_2[] = {
	3,	/* dB-1dot77(3) */
	2,	/* dB-3(2) */
	1,	/* dB-4dot77(1) */
	0,	/* dB-6(0) */
	4,	/* dB0(4) */
	5,	/* dB1(5) */
	6,	/* dB2(6) */
	7	/* dB3(7) */
};
static asn_INTEGER_specifics_t asn_SPC_p_a_specs_2 = {
	asn_MAP_p_a_value2enum_2,	/* "tag" => N; sorted by tag */
	asn_MAP_p_a_enum2value_2,	/* N => "tag"; sorted by N */
	8,	/* Number of elements in the maps */
	0,	/* Enumeration is not extensible */
	1,	/* Strict enumeration */
	0,	/* Native long size */
	0
};
static ber_tlv_tag_t asn_DEF_p_a_tags_2[] = {
	(ASN_TAG_CLASS_CONTEXT | (0 << 2)),
	(ASN_TAG_CLASS_UNIVERSAL | (10 << 2))
};
static /* Use -fall-defs-global to expose */
asn_TYPE_descriptor_t asn_DEF_p_a_2 = {
	"p-a",
	"p-a",
	p_a_2_free,
	p_a_2_print,
	p_a_2_constraint,
	p_a_2_decode_ber,
	p_a_2_encode_der,
	p_a_2_decode_xer,
	p_a_2_encode_xer,
	p_a_2_decode_uper,
	p_a_2_encode_uper,
	0,	/* Use generic outmost tag fetcher */
	asn_DEF_p_a_tags_2,
	sizeof(asn_DEF_p_a_tags_2)
		/sizeof(asn_DEF_p_a_tags_2[0]) - 1, /* 1 */
	asn_DEF_p_a_tags_2,	/* Same as above */
	sizeof(asn_DEF_p_a_tags_2)
		/sizeof(asn_DEF_p_a_tags_2[0]), /* 2 */
	&asn_PER_type_p_a_constr_2,
	0, 0,	/* Defined elsewhere */
	&asn_SPC_p_a_specs_2	/* Additional specs */
};

static asn_TYPE_member_t asn_MBR_PDSCH_ConfigDedicated_1[] = {
	{ ATF_NOFLAGS, 0, offsetof(struct PDSCH_ConfigDedicated, p_a),
		(ASN_TAG_CLASS_CONTEXT | (0 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_p_a_2,
		0,	/* Defer constraints checking to the member type */
		0,	/* No PER visible constraints */
		0,
		"p-a"
		},
};
static ber_tlv_tag_t asn_DEF_PDSCH_ConfigDedicated_tags_1[] = {
	(ASN_TAG_CLASS_UNIVERSAL | (16 << 2))
};
static asn_TYPE_tag2member_t asn_MAP_PDSCH_ConfigDedicated_tag2el_1[] = {
    { (ASN_TAG_CLASS_CONTEXT | (0 << 2)), 0, 0, 0 } /* p-a at 1269 */
};
static asn_SEQUENCE_specifics_t asn_SPC_PDSCH_ConfigDedicated_specs_1 = {
	sizeof(struct PDSCH_ConfigDedicated),
	offsetof(struct PDSCH_ConfigDedicated, _asn_ctx),
	asn_MAP_PDSCH_ConfigDedicated_tag2el_1,
	1,	/* Count of tags in the map */
	0, 0, 0,	/* Optional elements (not needed) */
	-1,	/* Start extensions */
	-1	/* Stop extensions */
};
asn_TYPE_descriptor_t asn_DEF_PDSCH_ConfigDedicated = {
	"PDSCH-ConfigDedicated",
	"PDSCH-ConfigDedicated",
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
	asn_DEF_PDSCH_ConfigDedicated_tags_1,
	sizeof(asn_DEF_PDSCH_ConfigDedicated_tags_1)
		/sizeof(asn_DEF_PDSCH_ConfigDedicated_tags_1[0]), /* 1 */
	asn_DEF_PDSCH_ConfigDedicated_tags_1,	/* Same as above */
	sizeof(asn_DEF_PDSCH_ConfigDedicated_tags_1)
		/sizeof(asn_DEF_PDSCH_ConfigDedicated_tags_1[0]), /* 1 */
	0,	/* No PER visible constraints */
	asn_MBR_PDSCH_ConfigDedicated_1,
	1,	/* Elements count */
	&asn_SPC_PDSCH_ConfigDedicated_specs_1	/* Additional specs */
};

