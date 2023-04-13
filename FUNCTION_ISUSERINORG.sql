DROP FUNCTION IF EXISTS isUserInOrg;

CREATE FUNCTION isUserInOrg(userID INTEGER,orgID INTEGER )
returns boolean

as
$$

begin
RETURN EXISTS (SELECT 1 FROM organizationuser
 WHERE (organizationuser.fk_userid = userID AND organizationuser.fk_organizationid = orgID));
 
end
$$
language plpgsql;