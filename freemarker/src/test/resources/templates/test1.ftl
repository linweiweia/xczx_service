<!DOCTYPE html>
<html>
<head>
        <meta charset="utf‐8">
        <title>Hello World!</title>
</head>
<body>
Hello ${name}!
<br/>
list指令用来遍历list中的数据
<table>
    <tr>
        <td>序号</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>钱包</td>
        <td>时间</td>
    </tr>
    <#--list指令用来遍历list中的数据-->
    <#--if指令和判断空值用在对象前加??（一般去判断list，基本数据类型用基本数据类型为空换成默认值）-->
    <#if stus??>
        <#list stus as stu>
            <tr>
                <td>${stu_index}</td>
                <td <#if (stu.name =='小明')!"我是默认值！">style="background:red;"</#if>>
                    ${stu.name}
                </td>
                <td>${stu.age}</td>
                <td>${stu.mondy}</td>
                <td>${stu.birthday?date}</td>
            </tr>
        </#list>
    </#if>
</table>


<br/><br/>
<#--获取map中的数据-->
第一种获取map中的数据[]  .
<#--为空默认值 ()!"默认值"-->
${(stuMap.stu1.name)!"我是默认值"}<br/>
${stuMap.stu2.name}<br/>
${(stuMap['stu1'].name)!"我是默认值"}<br/>
${stuMap['stu2'].name}<br/>

第二种获取map的数据(使用list做遍历)
<#list stuMap?keys as k>
    ${(stuMap[k].name)!"我是默认值"}<br/>
</#list>

<#--内建函数-->
${stus?size}

</body>
</html>