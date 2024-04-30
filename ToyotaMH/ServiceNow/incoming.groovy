if (firstSync) {
    // For the first sync: Decide the entity you want to create based on the remote issue type
    entity.tableName = "incident"
}

if (entity.tableName == "incident") {
    entity.short_description  = replica.summary
    entity.description        = replica.description
    entity.attachments        += replica.addedAttachments
    entity.comments           += replica.addedComments
    entity.u_ado_id            = replica.key

//STATE MAPPING
/*
On Hold>Awaiting Development	<	Analysis
On Hold>Awaiting Development	<	Development
On Hold>Awaiting Development	<	Ready for QA deployment
On Hold>Awaiting Development	<	Ready for Testing
On Hold>Awaiting Product Owner	<	Ready for PO Review
On Hold>Awaiting UAT	<	Ready for Dealer UAT
On Hold>Awaiting UAT	<	Deployed to Dealer UAT
On Hold>Awaiting for Prod	<	Ready for Dealer PROD
On Hold>Awaiting for Prod	<	Deployed to Dealer PROD
Resolved	<	Done
*/



switch(replica.status.name) { 
   case "Analysis": 
   case "Development":
   case "Ready for QA deployment":
   case "Ready for Testing":
         entity.state = "On Hold";
        entity.hold_reason = "Awaiting Development";
        break;

    case "Ready for PO Review":
         entity.state = "On Hold";
        entity.hold_reason = "Awaiting Product Owner";
        break;


   case "Ready for Dealer UAT": 
   case "Deployed to Dealer UAT":
        entity.state = "On Hold";
        entity.hold_reason = "Awaiting UAT";
        break;

   case "Ready for Dealer PROD": 
   case "Deployed to Dealer PROD":
        entity.state = "On Hold";
        entity.hold_reason = "Awaiting for Prod";
        break;
    
    case "Done":
        entity.state = "Resolved";
        break;

} 


}