/*
 * Generated by asn1c-0.9.22 (http://lionet.info/asn1c)
 * From ASN.1 module "EUTRA-RRC-Definitions"
 * 	found in "/home/liushifu/openair4G/openair2/RRC/LITE/MESSAGES/asn1c/ASN1_files/EUTRA-RRC-Definitions.asn"
 * 	`asn1c -gen-PER -fcompound-names -fnative-types`
 */

#ifndef	_CounterCheck_H_
#define	_CounterCheck_H_


#include <asn_application.h>

/* Including external dependencies */
#include "RRC-TransactionIdentifier.h"
#include "CounterCheck-r8-IEs.h"
#include <NULL.h>
#include <constr_CHOICE.h>
#include <constr_SEQUENCE.h>

#ifdef __cplusplus
extern "C" {
#endif

/* Dependencies */
typedef enum CounterCheck__criticalExtensions_PR {
	CounterCheck__criticalExtensions_PR_NOTHING,	/* No components present */
	CounterCheck__criticalExtensions_PR_c1,
	CounterCheck__criticalExtensions_PR_criticalExtensionsFuture
} CounterCheck__criticalExtensions_PR;
typedef enum CounterCheck__criticalExtensions__c1_PR {
	CounterCheck__criticalExtensions__c1_PR_NOTHING,	/* No components present */
	CounterCheck__criticalExtensions__c1_PR_counterCheck_r8,
	CounterCheck__criticalExtensions__c1_PR_spare3,
	CounterCheck__criticalExtensions__c1_PR_spare2,
	CounterCheck__criticalExtensions__c1_PR_spare1
} CounterCheck__criticalExtensions__c1_PR;

/* CounterCheck */
typedef struct CounterCheck {
	RRC_TransactionIdentifier_t	 rrc_TransactionIdentifier;
	struct CounterCheck__criticalExtensions {
		CounterCheck__criticalExtensions_PR present;
		union CounterCheck__criticalExtensions_u {
			struct CounterCheck__criticalExtensions__c1 {
				CounterCheck__criticalExtensions__c1_PR present;
				union CounterCheck__criticalExtensions__c1_u {
					CounterCheck_r8_IEs_t	 counterCheck_r8;
					NULL_t	 spare3;
					NULL_t	 spare2;
					NULL_t	 spare1;
				} choice;
				
				/* Context for parsing across buffer boundaries */
				asn_struct_ctx_t _asn_ctx;
			} c1;
			struct CounterCheck__criticalExtensions__criticalExtensionsFuture {
				
				/* Context for parsing across buffer boundaries */
				asn_struct_ctx_t _asn_ctx;
			} criticalExtensionsFuture;
		} choice;
		
		/* Context for parsing across buffer boundaries */
		asn_struct_ctx_t _asn_ctx;
	} criticalExtensions;
	
	/* Context for parsing across buffer boundaries */
	asn_struct_ctx_t _asn_ctx;
} CounterCheck_t;

/* Implementation */
extern asn_TYPE_descriptor_t asn_DEF_CounterCheck;

#ifdef __cplusplus
}
#endif

#endif	/* _CounterCheck_H_ */
#include <asn_internal.h>
