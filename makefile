# Constants

COLOR_RED=\033[0;31m
COLOR_GREEN=\033[0;32m
COLOR_YELLOW=\033[0;33m
COLOR_BLUE=\033[0;34m
COLOR_NONE=\033[0m
COLOR_CLEAR_LINE=\r\033[K

# Targets

help:
	@grep -E '^[0-9a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) \
		| sort \
		| awk 'BEGIN {FS = ":.*?## "}; {printf "$(COLOR_BLUE)%-20s$(COLOR_NONE) %s\n", $$1, $$2}'

.PHONY: clean repl test dist integration-test all

clean: ## Remove all built artefacts
	@echo 'Removing built artefacts'
	@lein clean

repl: ## Launch a REPL
	@echo 'Launching a REPL'
	@lein fig:build

test: ## Run unit tests
	@echo 'Running unit tests'
	@lein fig:test

dist: ## Build the distribution
	@echo "Building the distribution"
	@lein cljsbuild once

integration-test: dist ## Run integration tests
	@echo "Running integration tests"
	@lein test :integration

all: clean test integration-test ## Run all tests and build the distribution
