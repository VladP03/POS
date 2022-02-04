db.createUser(
    {
        user: "user",
        pwd: "user",
        roles: [
            {
                role: "readWrite",
                db: "Orders"
            }
        ]
    }
);

db.createUser(
    {
        user: "dbOwner",
        pwd: "dbOwner",
        roles: [
            {
                role: "dbOwner",
                db: "Orders"
            }
        ]
    }
);