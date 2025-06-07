import pyshark

arquivo_pcap = "ssh.pcapng"

# Capturar todos os pacotes SSH
cap = pyshark.FileCapture(arquivo_pcap, display_filter="ssh", keep_packets=False)

print("Análise de Criptografia SSH")
print("=" * 50)

# Variáveis para armazenar informações de criptografia
encryption_info = {
    'kex_algorithms': None,
    'server_host_key_algorithms': None,
    'encryption_algorithms_client_to_server': None,
    'encryption_algorithms_server_to_client': None,
    'mac_algorithms_client_to_server': None,
    'mac_algorithms_server_to_client': None,
    'compression_algorithms_client_to_server': None,
    'compression_algorithms_server_to_client': None
}

for i, pkt in enumerate(cap, 1):
    try:
        if hasattr(pkt, 'ssh'):
            # Capturar informações do handshake inicial
            if hasattr(pkt.ssh, 'kex_algorithms'):
                encryption_info['kex_algorithms'] = pkt.ssh.kex_algorithms
            if hasattr(pkt.ssh, 'server_host_key_algorithms'):
                encryption_info['server_host_key_algorithms'] = pkt.ssh.server_host_key_algorithms
            if hasattr(pkt.ssh, 'encryption_algorithms_client_to_server'):
                encryption_info['encryption_algorithms_client_to_server'] = pkt.ssh.encryption_algorithms_client_to_server
            if hasattr(pkt.ssh, 'encryption_algorithms_server_to_client'):
                encryption_info['encryption_algorithms_server_to_client'] = pkt.ssh.encryption_algorithms_server_to_client
            if hasattr(pkt.ssh, 'mac_algorithms_client_to_server'):
                encryption_info['mac_algorithms_client_to_server'] = pkt.ssh.mac_algorithms_client_to_server
            if hasattr(pkt.ssh, 'mac_algorithms_server_to_client'):
                encryption_info['mac_algorithms_server_to_client'] = pkt.ssh.mac_algorithms_server_to_client
            if hasattr(pkt.ssh, 'compression_algorithms_client_to_server'):
                encryption_info['compression_algorithms_client_to_server'] = pkt.ssh.compression_algorithms_client_to_server
            if hasattr(pkt.ssh, 'compression_algorithms_server_to_client'):
                encryption_info['compression_algorithms_server_to_client'] = pkt.ssh.compression_algorithms_server_to_client

    except AttributeError as e:
        continue

# Exibir informações de criptografia
print("\nDetalhes da Criptografia SSH:")
print("-" * 30)
for key, value in encryption_info.items():
    if value:
        print(f"\n{key.replace('_', ' ').title()}:")
        print(f"{value}")

cap.close()
