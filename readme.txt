<Host appBase="webapps" name="bnews.vne" unpackWARs="true" autoDeploy="true">
	<Alias>bsong.vne</Alias>
	<Context debug="0" docBase="realPathProject" path="" reloadable="true" />
</Host>


VirtualHost chạy multi domain
Muốn tạo domain ảo chạy trong máy, ví dụ:
bsong.vne ta tiến hành các bước sau:
B1.Sửa file hosts
C:\Windows\System32\drivers\etc
B2. Sửa file server.xml
<Host appBase="webapps" name="bsong.vne" unpackWARs="true" autoDeploy="true">
<Alias>bsong.vne</Alias>
<Context debug="0" docBase="realPathProject" path="" reloadable="true" />
</Host>

Biểu thức chính uy 
^ : bat dau
$ :kết thúc
[a-z] : 1 kí tự từ a đến z viết thường
[A-Z] : 1 kí tự từ a đến z viết hoa
([a-z]*) : đại diện 0 hoặc nhiều kí tự từ a-z
([A-Z]*) : đại diện 0 hoặc nhiều kí tự từ A-Z
(.*) : đại diện 0 hoặc nhiều kí từ bất kỳ
(.+) : đại diện 1 hoặc nhiều kí từ bất kỳ
([a-zA-Z]* || +) : đại diện cho các kí tự từ a -z A-z 
[0-9] : các số từ 0-9
([0-9]+) : các số từ 1-n
([0-9]*) : các số từ 0-n