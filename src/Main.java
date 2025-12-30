void main() {

    String filePath = "input.txt";

    try (FileChannel fileChannel = FileChannel.open(Paths.get(filePath), StandardOpenOption.READ)) {

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        StringBuilder content = new StringBuilder();

        while (true) {
            buffer.clear();
            int bytesRead = fileChannel.read(buffer);

            if (bytesRead == -1) {
                break;
            }

            buffer.flip();

            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            String chunk = new String(bytes, StandardCharsets.UTF_8);

            System.out.print(chunk);

            content.append(chunk);
        }

        System.out.println("END OF FILE");

    } catch (IOException e) {
        System.err.println(e.getMessage());
    }

    System.out.println("-----------------------------------------------------------");
    System.out.println("-----------------------------------------------------------");
    System.out.println("-----------------------------------------------------------");

    String sourceFile = "source.bin";
    String destFile = "copy.bin";

    try (FileChannel sourceChannel = FileChannel.open(Paths.get(sourceFile), StandardOpenOption.READ);
         FileChannel destChannel = FileChannel.open(Paths.get(destFile), StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        long startTime = System.currentTimeMillis();
        long totalBytesCopied = 0;

        while (true) {
            buffer.clear();
            int bytesRead = sourceChannel.read(buffer);
            if (bytesRead == -1) {
                break;
            }
            buffer.flip();

            while (buffer.hasRemaining()) {
                destChannel.write(buffer);
            }

            totalBytesCopied += bytesRead;
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("totalBytesCopied = " + totalBytesCopied);
        System.out.println("duration = " + duration);

    } catch (IOException e) {
        System.err.println(e.getMessage());
    }
}

