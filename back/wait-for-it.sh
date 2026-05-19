#!/bin/bash
# Script to wait for a service to be ready
# Usage: ./wait-for-it.sh host:port [-s] [-t timeout] [-- command args]

HOST_PORT=$1
STRICT=0
TIMEOUT=15
COMMAND=()

shift

while [[ $# -gt 0 ]]; do
  case "$1" in
    -s|--strict) STRICT=1; shift ;;
    -t|--timeout) TIMEOUT=$2; shift 2 ;;
    --) shift; COMMAND=("$@"); break ;;
    *) COMMAND+=("$1"); shift ;;
  esac
done

HOST=${HOST_PORT%:*}
PORT=${HOST_PORT#*:}

if [[ -z "$HOST" || -z "$PORT" ]]; then
  echo "Usage: $0 host:port [-s] [-t timeout] [-- command args]"
  exit 1
fi

echo "Waiting for $HOST:$PORT to be ready..."

timeout=$(($(date +%s) + TIMEOUT))
while [[ $(date +%s) -lt $timeout ]]; do
  if nc -z "$HOST" "$PORT" 2>/dev/null; then
    echo "$HOST:$PORT is up!"
    if [[ ${#COMMAND[@]} -gt 0 ]]; then
      exec "${COMMAND[@]}"
    else
      exit 0
    fi
  fi
  sleep 1
done

echo "Timeout waiting for $HOST:$PORT"
exit 1
