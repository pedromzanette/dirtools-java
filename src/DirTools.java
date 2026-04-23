import java.io.File;

public class DirTools {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Uso inválido.");
            return;
        }

        String comando = args[0];

        switch (comando) {
            case "find":
                if (args.length < 3) {
                    System.out.println("Uso: find root substring");
                    return;
                }
                File diretorioRaizFind = new File(args[1]);
                String substring = args[2];
                find(diretorioRaizFind, substring);
                break;

            case "getSubSize":
                if (args.length < 2) {
                    System.out.println("Uso: getSubSize root");
                    return;
                }
                File diretorioRaizSub = new File(args[1]);
                getSubSize(diretorioRaizSub);
                break;

            case "getSize":
                if (args.length < 2) {
                    System.out.println("Uso: getSize dirname");
                    return;
                }
                File diretorio = new File(args[1]);
                System.out.println(diretorio.getAbsolutePath() + " -> " + getSize(diretorio) + " bytes");
                break;

            case "depthList":
                if (args.length < 2) {
                    System.out.println("Uso: depthList root");
                    return;
                }

                File diretorioDepth = new File(args[1]);

                if (!diretorioDepth.exists() || !diretorioDepth.isDirectory()) {
                    System.out.println("Diretório inválido.");
                    break;
                }

                depthList(diretorioDepth);
                break;

            default:
                System.out.println("Comando inválido.");
        }
    }

    public static void find(File atual, String substring) {
        if (atual.isFile()) {
            if (atual.getName().contains(substring)) {
                System.out.println(atual.getAbsolutePath());
            }
        } else if (atual.isDirectory()) {
            File[] arquivos = atual.listFiles();

            if (arquivos != null) {
                for (File arquivo : arquivos) {
                    find(arquivo, substring);
                }
            }
        }
    }

    public static void getSubSize(File atual) {
        if (atual.isDirectory()) {
            System.out.println(atual.getAbsolutePath() + " -> " + getSize(atual) + " bytes");

            File[] arquivos = atual.listFiles();

            if (arquivos != null) {
                for (File arquivo : arquivos) {
                    if (arquivo.isDirectory()) {
                        getSubSize(arquivo);
                    }
                }
            }
        }
    }

    public static long getSize(File atual) {
        if (atual.isFile()) {
            return atual.length();
        }

        long total = 0;
        File[] arquivos = atual.listFiles();

        if (arquivos != null) {
            for (File arquivo : arquivos) {
                total += getSize(arquivo);
            }
        }

        return total;
    }

    public static int maxDepth(File diretorio, int nivelAtual) {
        int maiorNivel = nivelAtual;

        File[] arquivos = diretorio.listFiles();

        if (arquivos != null) {
            for (File arquivo : arquivos) {
                if (arquivo.isDirectory()) {
                    int profundidade = maxDepth(arquivo, nivelAtual + 1);
                    if (profundidade > maiorNivel) {
                        maiorNivel = profundidade;
                    }
                }
            }
        }
        return maiorNivel;
    }

    public static void listarDiretoriosMaisProfundos(File diretorio, int nivelAtual, int profundidadeMax) {
        if (nivelAtual == profundidadeMax) {
            System.out.println(diretorio.getAbsolutePath());
            return;
        }

        File[] arquivos = diretorio.listFiles();

        if (arquivos != null) {
            for (File arquivo : arquivos) {
                if (arquivo.isDirectory()) {
                    listarDiretoriosMaisProfundos(arquivo, nivelAtual + 1, profundidadeMax);
                }
            }
        }
    }

    public static void listarArquivosMaisProfundos(File diretorio, int nivelAtual, int profundidadeMax) {
        if (nivelAtual == profundidadeMax) {
            File[] arquivos = diretorio.listFiles();

            if (arquivos != null) {
                for (File arquivo : arquivos) {
                    if (arquivo.isFile()) {
                        System.out.println(arquivo.getName());
                    }
                }
            }
            return;
        }

        File[] arquivos = diretorio.listFiles();

        if (arquivos != null) {
            for (File arquivo : arquivos) {
                if (arquivo.isDirectory()) {
                    listarArquivosMaisProfundos(arquivo, nivelAtual + 1, profundidadeMax);
                }
            }
        }
    }

    public static void depthList(File diretorio) {
        int profundidadeMaxima = maxDepth(diretorio, 0);

        System.out.println("Diretórios mais profundos:");
        listarDiretoriosMaisProfundos(diretorio, 0, profundidadeMaxima);

        System.out.println("Arquivos encontrados:");
        listarArquivosMaisProfundos(diretorio, 0, profundidadeMaxima);
    }
}