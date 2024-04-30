if (entity.tableName == "incident") {
    replica.key            = entity.key
    replica.summary        = entity.short_description
    replica.description    = entity.description
    replica.comments       = entity.comments

    replica.state          = entity.state
    replica.hold_reason    = entity.hold_reason

    replica.subcategory    = entity.subcategory

    replica.priority       = entity.priority

}
