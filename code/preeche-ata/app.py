from flask import Flask, render_template, request
from docx import Document

app = Flask(__name__)

# Função para preencher a ATA com os dados fornecidos, incluindo tabelas
def preencher_ata(modelo_path, dados, output_path):
    # Abre o documento
    doc = Document(modelo_path)

    # Preencher parágrafos
    for paragrafo in doc.paragraphs:
        for marcador, valor in dados.items():
            if marcador in paragrafo.text:
                paragrafo.text = paragrafo.text.replace(marcador, valor)
    
    # Preencher tabelas
    for tabela in doc.tables:
        for linha in tabela.rows:
            for celula in linha.cells:
                for marcador, valor in dados.items():
                    if marcador in celula.text:
                        celula.text = celula.text.replace(marcador, valor)

    # Salva o documento preenchido
    doc.save(output_path)
    print(f"Ata salva em: {output_path}")

# Página inicial
@app.route('/')
def index():
    return render_template('index.html')

# Rota para preencher a ata
@app.route('/preencher_ata', methods=['POST'])
def preencher_ata_route():
    dados_reuniao = {
        "{data}": request.form['data'],
        "{horaInicio}": request.form['horaInicio'],
        "{horaFinal}": request.form['horaFinal'],
        "{formato}": request.form['formato'],
        "{pauta}": request.form['pauta'],
        "{resumo}": request.form['resumo'],
        "{responsavel}": request.form['responsavel']
    }

    modelo_ata = "template-ata.docx"
    saida_ata = f"ata_reuniao_{dados_reuniao['{data}']}.docx"

    preencher_ata(modelo_ata, dados_reuniao, saida_ata)
    
    return f"Ata gerada e salva em: {saida_ata}"

if __name__ == '__main__':
    app.run(debug=True)
