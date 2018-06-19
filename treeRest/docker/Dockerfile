FROM java:8

COPY docker/wrapper.sh wrapper.sh

COPY target/tree-rest-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/app.properties app.properties

COPY data/ /usr/data/

RUN /bin/bash -c 'chmod +x /wrapper.sh'
RUN /bin/bash -c 'touch /app.jar'

ENTRYPOINT ["/bin/bash", "/wrapper.sh"]