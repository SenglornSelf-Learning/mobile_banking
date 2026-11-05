Mobile Banking - Account Management Entity Model
Backup of the entity and relationship explanation.
Package: src/main/java/com/mobile_banking/account/model

The first mapping was not a valid JPA model. The same links were declared twice, and two classes claimed the same table, so Hibernate could not start cleanly. The entities now use one association class per link.


WHAT WAS WRONG
--------------

1. Users mapped roles with @ManyToMany and a join table named user_roles, while UserRole mapped the same link again as its own entity (table users_roles).

2. Role mapped authorities with @ManyToMany and a join table named role_authorities, while RoleAuthority mapped that same table as its own entity.

3. Account and UserAccount both used the table user_accounts. UserAccount also had no @Id, so it was not a valid entity.

4. AccountType sat on Users. A product type belongs on the bank account (savings, current, and so on), not on the person.

5. UserRole used CascadeType.REMOVE on the user side. Removing a role assignment would delete the user. The foreign-key side of an association must not cascade removal to the parent.

6. BaseEntity had no @MappedSuperclass, so the audit columns (cret_id, cret_ip, cret_date, modi_id, modi_ip, modi_date, del_yn) were never part of the tables. BaseEntity must stay annotated with @MappedSuperclass for those columns to be created.


MODEL
-----

Users 1 ---- * UserRole        * ---- 1 Role
Role  1 ---- * RoleAuthority   * ---- 1 Authority

Users 1 ---- * UserAccount     * ---- 1 Account * ---- 1 AccountType

UserAccount is what allows a joint account: many users on one account, and many accounts per user.

The foreign key lives on the association entity (UserRole, RoleAuthority, UserAccount). The parent side only uses mappedBy. Do not also add @ManyToMany @JoinTable for the same link. An association entity is required here because each link extends BaseEntity and needs its own id plus audit columns. 
A plain @JoinTable cannot store those columns.


TABLES
------

Entity          Table                Owns
------          -----                ----
Users           tbl_users            profile only
UserRole        tbl_user_roles       one user + one role, unique together (uk_user_role)
Role            tbl_roles            role name, unique
RoleAuthority   tbl_role_authorities     one role + one authority, unique together (uk_role_authority)
Authority       tbl_authorities          authority name, unique
UserAccount     tbl_user_accounts    one user + one account, unique together (uk_user_account)
Account         tbl_accounts         account number (unique) + account type
AccountType     tbl_account_types    type name, unique

Each association row is unique on its pair of foreign keys:
- tbl_user_roles     (user_id, role_id)
- tbl_role_authorities   (role_id, authority_id)
- tbl_user_accounts  (user_id, account_id)


RELATIONSHIP RULES
------------------

- Users.userAccounts      OneToMany  mappedBy = "user"     cascade ALL, orphanRemoval
- Users.userRoles         OneToMany  mappedBy = "user"     cascade ALL, orphanRemoval
- Account.accountType     ManyToOne  JoinColumn account_type_id, nullable = false, LAZY
- Account.userAccounts    OneToMany  mappedBy = "account"  cascade ALL, orphanRemoval
- AccountType.accounts    OneToMany  mappedBy = "accountType", LAZY, no cascade
- UserAccount.user        ManyToOne  JoinColumn user_id, nullable = false, LAZY, no cascade
- UserAccount.account     ManyToOne  JoinColumn account_id, nullable = false, LAZY, no cascade
- UserRole.user           ManyToOne  JoinColumn user_id, nullable = false, LAZY, no cascade
- UserRole.role           ManyToOne  JoinColumn role_id, nullable = false, LAZY, no cascade
- Role.userRoles          OneToMany  mappedBy = "role"     cascade ALL, orphanRemoval
- Role.roleAuthorities    OneToMany  mappedBy = "role"     cascade ALL, orphanRemoval
- Authority.roleAuthorities OneToMany mappedBy = "authority", LAZY, no cascade
- RoleAuthority.role      ManyToOne  JoinColumn role_id, nullable = false, LAZY, no cascade
- RoleAuthority.authority ManyToOne  JoinColumn authority_id, nullable = false, LAZY, no cascade

Deleting a user deletes that user's UserAccount and UserRole rows. It does not delete Account, Role, or Authority.
Deleting a role deletes that role's UserRole and RoleAuthority rows. It does not delete Users or Authority.
Deleting an AccountType does not delete accounts.


COLUMN NOTES
------------

- password length must be long enough for a hash. varchar(30) cannot store a BCrypt hash (60 characters). Use at least varchar(100).
- account_number is unique. Length 20.
- role_name and authority_name are unique.
- email and student_card_no are unique.
- Fetch type on associations is LAZY.


DATABASE RESET
--------------

spring.jpa.hibernate.ddl-auto is update. An existing user_accounts table created by the old Account mapping will not reshape itself into the join table. If the application was already started with the old entities, drop these tables in MySQL and restart so Hibernate creates them from the current entities:

- user_accounts
- users_roles
- user_roles
- role_authorities

Then let Hibernate create tbl_users, tbl_user_roles, tbl_roles, authorities, role_authorities, tbl_user_accounts, tbl_accounts, and tbl_account_types.
