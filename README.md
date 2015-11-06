# Drools-workshop
Here are some projects for a basic to intermediate Drools Workshop

- my-first-drools-project: basic project created from a maven archetype, for more info about how to create the project look here: http://salaboy.com/2015/10/20/back-to-the-basics-getting-started-with-drools-6-3-0-final/ and for tests using arquillian look here: http://salaboy.com/2015/10/21/back-to-the-basics-2-testing-rules-with-arquillian-basics/
- drools-kie-ci-example: very simple project using kie-ci to resolve KJars using maven, for more information look at: http://salaboy.com/2015/10/23/back-to-basics-3-using-drools-kie-ci-continuous-integration/
- drools-rest-swarm: Different examples for exposing Rules via REST endpoints using Wildfly Swarm: for more information look here: http://salaboy.com/2015/10/27/back-to-basics-4-exposing-our-rules-via-rest-jax-rs-using-wildfly-swarm/ for bundling our service using Docker look at: http://salaboy.com/2015/10/29/back-to-basics-5-cloud-rules-drools-docker/
  -   drools-user-*: basic example:  http://salaboy.com/2015/10/27/back-to-basics-4-exposing-our-rules-via-rest-jax-rs-using-wildfly-swarm/
  -   drools-shopping-*: Drools Example shopping cart exposed via REST services. 
  -   drools-cep: Drools Fusion example
- Kie Server: modified Kie Server bundled with Docker to use along the examples
  - kie-server-cmd-marshaller: simple project to generate JSON format of the commands to interact with KIE Server
- Phreak Inspector: very simple project to draw the RETE network using the Graphviz language


# Building the projects (To avoid big downloads on the workshops)
  # Requirements:
    - JDK 1.7 + 
    - Maven 3.2.3 + 
    - Git tools (https://git-scm.com/downloads)
    - Docker (https://docs.docker.com)
    - An IDE will be good

- Clone the projects to your local environment: git clone https://github.com/Salaboy/drools-workshop.git
- cd drools-workshop/
  - cd my-first-drools-project/
    - mvn clean install
    - cd ..
  - cd drools-kie-ci-example/
    - mvn clean install
    - cd ..
  - drools-rest-swarm:
    - mvn clean install
    - cd drools-jax-rs/
      - mvn clean install docker:build (require having docker installed and being in a docker configured terminal)
      - cd ..
    - cd drools-shopping-services/
      - mvn clean install docker:build (require having docker installed and being in a docker configured terminal)
      - cd ..
    - cd drools-cep-services/
      - mvn clean install docker:build (require having docker installed and being in a docker configured terminal)
      - cd .. 
    - cd ..
  - cd kie-server/
    - mvn clean install
    - cd kie-server-docker-build/
      - mvn clean install docker:build (require having docker installed and being in a docker configured terminal)
      - cd ..
  - cd phreak-inspector/
    - mvn clean install

Docker Specifics
  If you want you can download some of my docker images, this usually takes time so I recommend you to download these images before going to the workshop. You can execute these commands once you installed docker (and you are in a docker enabled terminal, I'm using Kitematic in Mac OSX and for that reason you need to start it and then open a comand line (CLI) from Kitematic). My Docker images are here: https://hub.docker.com/u/salaboy/
  - docker pull salaboy/drools-jax-rs
  - docker pull salaboy/drools-shopping-services
  - docker pull salaboy/drools-workshop-kie-server
  - docker pull salaboy/drools-cep-services
  
Please create an issue here if you find a problem with this instructions. 
