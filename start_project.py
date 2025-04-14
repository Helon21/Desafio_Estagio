import subprocess
import socket
import time
import os

def start_docker_compose():
    print("Starting Docker Compose...")
    try:
        subprocess.check_call(["docker-compose", "up", "-d", "--build"])
    except subprocess.CalledProcessError as e:
        print("Erro ao iniciar docker-compose:", e)
        exit(1)

def wait_for_postgres(host="localhost", port=5432, timeout=60):
    print(f"Waiting for PostgreSQL at {host}:{port}...")
    start_time = time.time()
    while True:
        try:
            with socket.create_connection((host, port), timeout=2):
                print("PostgreSQL is now available!")
                return
        except (OSError, ConnectionRefusedError):
            elapsed = time.time() - start_time
            if elapsed > timeout:
                raise Exception(f"Timeout: PostgreSQL não disponível após {timeout}s.")
            print(f"Waiting... ({int(elapsed)}s)")
            time.sleep(2)

if __name__ == '__main__':
    start_docker_compose()
    wait_for_postgres(host="localhost", port=5432)
