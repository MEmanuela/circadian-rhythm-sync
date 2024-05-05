✅ done
<br>
❌ doesn't work
<br>
❗ to do
<br>
❓ don't know how to do
<hr>


1. start the app using docker-compose ✅
2. security leftovers? 

- create roles in db manually
  - insert roles using a liquibase change ✅

### what can I do so far:
- register user with username and password ✅ - result is 200 and No Content
- login user with username and password ✅ - result is 200 and generated access token
- logout ✅ - token is expired
- change user password ✅
- access resources based on roles ✅
- can change only your password ✅
- ~~separate methods for registering an admin and a user ❌~~
  - insert an admin with default values (admin - admin) using a liquibase change ✅
- gradle script to run build+clean+jar ✅

