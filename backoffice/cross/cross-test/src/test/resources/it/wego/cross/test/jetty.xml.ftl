[#ftl]<Configure  class="org.eclipse.jetty.server.Server">

    <New id="CrossDS" class="org.eclipse.jetty.plus.jndi.Resource">
        <Arg>jdbc/CrossDS</Arg>
        <Arg>
            <New class="com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource">
                <Set name="Url">jdbc:mysql://${host}:3306/${crossDb}?autoReconnect=true&amp;useUnicode=true&amp;characterEncoding=UTF-8</Set>
                <Set name="User">${crossUser}</Set>
                <Set name="Password">${crossPsw}</Set>
            </New>
        </Arg>
    </New>
    <New id="ActivitiDS" class="org.eclipse.jetty.plus.jndi.Resource">
        <Arg>jdbc/ActivitiDS</Arg>
        <Arg>
            <New class="com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource">
                <Set name="Url">jdbc:mysql://${host}:3306/${activitiDb}?autoReconnect=true&amp;useUnicode=true&amp;characterEncoding=UTF-8</Set>
                <Set name="User">${activitiUser}</Set>
                <Set name="Password">${activitiPsw}</Set>
            </New>
        </Arg>
    </New>

</Configure>       