create or replace function isNumber

(p in varchar2)

return number

is

result number;

begin

result := to_number(p);

return 1;

exception

when VALUE_ERROR then return 0;

end;
/