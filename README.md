<h1>Trabalho de seguranca</h1>

<h2>Foi utilizado o Maven para project management, e Tomcat 8.5.23 para o server</h2>

<h4>Como correr</h4>
<p>Deve so ser preciso fazer o import das dependencies do Maven e configurar o server Tomcat, provavelmente fazer npm clean install.</p>

<h4>Coisas uteis saber:</h4>
<p>1- A conta de admin tem </p>
<p>username: root</p>
<p>password: md94jgjewog$sg</p>
<p>2- Existe um ficheiro com o nome Constants.java, que tem várias definiçoes da webapp, algumas delas podem ser mudadas como der jeito</p>
<p>3- Este trabalho já utiliza Capabilities. O tempo de duração de uma Capability está no ficheiro de nome Constants.java tal como indicado acima, com o nome CAPABILITY_EXPIRE_TIME que tem o valor default de 2 horas. A partir do momento que uma Capability expira, quaisquer operações que se tentem efectuar serão recusadas devolvendo uma mensagem de "Permission Denied".
