# Use an official lightweight OpenJDK image
FROM openjdk:17-slim

# Set the working directory inside the container
WORKDIR /app

# Copy all the project files into the container
COPY src/ /app/src/
COPY web/ /app/web/
COPY css/ /app/css/
COPY js/ /app/js/

# Create a bin directory for compiled classes
RUN mkdir -p /app/bin

# Compile all Java files from the src directory into the bin directory
RUN javac -d /app/bin $(find /app/src -name "*.java")

# Expose port 8080 by default (though cloud providers will override this)
EXPOSE 8080

# Command to run the Java server
# Cloud providers (like Railway or Render) inject a $PORT environment variable.
# We pass this to the Java Main class, defaulting to 8080 if not set.
CMD ["sh", "-c", "java -cp /app/bin Main ${PORT:-8080}"]
