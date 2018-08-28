#跳转到tomcat挂载根目录 解压war包
cd /usr/erp/webapps/ROOT/ &&jar -xvf erp.war;
#重启docker容器
docker restart erp;
