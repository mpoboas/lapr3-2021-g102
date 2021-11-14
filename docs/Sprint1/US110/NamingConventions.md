#Naming Conventions for PL/SQL
In general, ORACLE is not case sensitive with names. A variable named personname is equal to one named PersonName, as well as to one named PERSONNAME. Some products (e.g. TMDA by Trivadis, APEX, OWB) put each name within double quotes (") so ORACLE will treat these names to be case sensitive. Using case sensitive variable names force developers to use double quotes for each reference to the variable. Our recommendation is to write all names in lowercase and to avoid double quoted identifiers.

A widely used convention is to follow a {prefix}variablecontent{suffix} pattern.

The following table shows a possible set of naming conventions.

| Identifier                   | Prefix | Suffix | Example           |
|------------------------------|--------|--------|-------------------|
| Global Variable              | g_     |        | g_version         |
| Local Variable               | l_     |        | l_version         |
| Cursor                       | c_     |        | c_employees       |
| Record                       | r_     |        | r_employee        |
| Array / Table                | t_     |        | t_employees       |
| Object                       | o_     |        | o_employee        |
| Cursor Parameter             | p_     |        | p_empno           |
| In Parameter                 | in_    |        | in_empno          |
| Out Parameter                | out_   |        | out_ename         |
| In/Out Parameter             | io_    |        | io_employee       |
| Record Type Definitions      | r_     | _type  | r_employee_type   |
| Array/Table Type Definitions | t_     | _type  | t_employees_type  |
| Exception                    | e_     |        | e_employee_exists |
| Constants                    | co_    |        | co_empno          |
| Subtypes                     |        | _type  | big_string_type   |