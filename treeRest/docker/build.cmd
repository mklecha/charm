copy Dockerfile ../Dockerfile
cd ..
docker build -t pu/charm .
del Dockerfile
cd docker