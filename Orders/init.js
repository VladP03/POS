db.createUser(
    {
        user: "user",
        pwd: "user",
        roles: [
            {
                role: "readWrite",
                db: "orders"
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
                db: "orders"
            }
        ]
    }
);