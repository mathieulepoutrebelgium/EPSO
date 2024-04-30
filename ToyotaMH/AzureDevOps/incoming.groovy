if(firstSync){
   // Set type name from source entity, if not found set a default
   workItem.projectKey  =  "MHNA-DMS"
   workItem.typeName = nodeHelper.getIssueType(replica.type?.name)?.name ?: "Bug";
}

workItem.summary      = replica.summary
workItem.description  = replica.description
//workItem."ServiceNow ID" = replica.key

workItem.comments     = commentHelper.mergeComments(workItem, replica)
workItem.labels       = replica.labels


//STATE MAPPING
// if snow is On Hold>Awaiting Development then ADO becomes New

if(replica.state == "On Hold" && replica.hold_reason == "Awaiting Development"){
  workItem.setStatus("New")

}

// PRIORITY TO SEVERITY
def severityMap = [
  "1 - Critical": "1 - Critical",
  "2 - High":"2 - High",
  "3 - Moderate":"3 - Medium",
  "4 - Low":"4 - Low"

]

workItem."Microsoft.VSTS.Common.Severity" = severityMap[replica.priority.name]


//AREA PATH
// def areaPathHashMap = [
//     "bc admin": "TMHNA-DMS\\D365 BC",
//     "BC Equipment": "TMHNA-DMS\\D365 BC",
//     "bc finance": "TMHNA-DMS\\D365 BC",
//     "bc part": "TMHNA-DMS\\D365 BC",
//     "bc rental": "TMHNA-DMS\\D365 BC",
//     "bc service": "TMHNA-DMS\\D365 BC",
//     "fs service": "TMHNA-DMS\\Active Development\\Service\\Field Service",
//     "FS Web": "TMHNA-DMS\\Active Development\\Service\\Field Service"
// ]

// workItem.areaPath = areaPathHashMap[replica.subcategory] ?: "replace this string with a default area path of your choosing"

