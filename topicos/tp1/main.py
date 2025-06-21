import numpy as np
import random
import time

def generate_problem_instance(n, m, num_attributes=3):
    """
    Gera uma instância do Problema da Diversidade Máxima com saídas detalhadas.
    """
    print("--- ETAPA 1: GERAÇÃO DA INSTÂNCIA DO PROBLEMA ---")
    print(f"Objetivo: De um conjunto de {n} elementos, selecionar um subconjunto de {m} elementos com a máxima diversidade.")
    

    # Gera 'n' elementos com 'num_attributes' aleatórios
    elements = np.random.rand(n, num_attributes)
    print(f"-> Foram criados {n} elementos, cada um com {num_attributes} atributos numéricos aleatórios.")
    print ("   Elementos gerados:")
    for idx, elem in enumerate(elements):
        print(f"   Elemento {idx}: {elem}")
    
    # Calcula a matriz de distâncias euclidianas
    distance_matrix = np.zeros((n, n))
    print("-> Calculando a matriz de distâncias simétrica...")
    print("   Esta matriz armazenará a diversidade d(i,j) entre cada par de elementos (i, j).")
    
    
    for i in range(n):
        for j in range(i + 1, n):
            dist = np.linalg.norm(elements[i] - elements[j])
            distance_matrix[i, j] = distance_matrix[j, i] = dist
    
    print("-> Matriz de distâncias calculada. Instância do problema gerada.\n")
    
    return distance_matrix, m

def calculate_diversity(solution, distance_matrix):
    """
    Calcula o valor da função objetivo (diversidade total) para uma dada solução.
    """
    total_diversity = 0
    for i in range(len(solution)):
        for j in range(i + 1, len(solution)):
            idx1 = solution[i]
            idx2 = solution[j]
            total_diversity += distance_matrix[idx1, idx2]
    return total_diversity

def greedy_constructive_heuristic(distance_matrix, m):
    """
    Constrói uma solução inicial utilizando uma heurística gulosa, com saídas detalhadas.
    """
    print("--- ETAPA 2: CONSTRUÇÃO DA SOLUÇÃO INICIAL (HEURÍSTICA GULOSA) ---")
    print("Estratégia: Iniciar a solução com um elemento e adicionar iterativamente o elemento que maximiza a soma das distâncias ao conjunto atual.")
    

    n = distance_matrix.shape[0]
    remaining_elements = list(range(n))
    
    # Inicia a solução com um elemento aleatório
    start_node = random.choice(remaining_elements)
    solution = [start_node]
    remaining_elements.remove(start_node)
    
    print(f"\nPasso 1: O primeiro elemento da solução, escolhido aleatoriamente, é o: {start_node}")
    print(f"   Solução atual: {solution}")
    

    while len(solution) < m:
        print(f"\nPasso {len(solution) + 1}: Buscando o melhor elemento para adicionar à solução {solution}...")
        best_candidate = -1
        max_contribution = -1
        
        # Encontra o elemento com a maior contribuição marginal
        for candidate_idx in remaining_elements:
            current_contribution = sum(distance_matrix[candidate_idx, sol_idx] for sol_idx in solution)
            print(f"  - Candidato {candidate_idx}: Contribuição marginal de diversidade = {current_contribution:.2f}")
            
            if current_contribution > max_contribution:
                max_contribution = current_contribution
                best_candidate = candidate_idx
        
        # Adiciona o melhor candidato à solução
        print(f"  -> O candidato com a maior contribuição marginal de diversidade é o {best_candidate} (contribuição: {max_contribution:.2f}).")
        solution.append(best_candidate)
        remaining_elements.remove(best_candidate)
        print(f"   Solução atual: {solution}")
        
        
    print("\n--- SOLUÇÃO INICIAL CONSTRUÍDA ---\n")
    
    return solution

def local_search_first_improvement(solution, distance_matrix, m):
    """
    Aplica uma busca local com a estratégia 'First Improvement', com saídas detalhadas.
    """
    print("--- ETAPA 3: REFINAMENTO DA SOLUÇÃO (BUSCA LOCAL) ---")
    print("Estratégia 'First Improvement': Avaliar movimentos de troca (swap). O primeiro que resultar em melhora na função objetivo é aceito e a busca reinicia.")
    
    n = distance_matrix.shape[0]
    current_solution = list(solution)
    
    iteration = 1
    while True:
        print(f"\nIteração da Busca Local #{iteration}:")
        current_diversity = calculate_diversity(current_solution, distance_matrix)
        print(f"   Solução atual: {current_solution} | Valor da Função Objetivo (Diversidade): {current_diversity:.2f}")
        
        found_improvement = False
        
        non_solution_elements = [i for i in range(n) if i not in current_solution]
        
        # Itera por todos os movimentos de troca (swap)
        for i_sol_idx, element_in in enumerate(current_solution):
            for element_out in non_solution_elements:
                
                print(f"  - Avaliando movimento de troca: remover '{element_in}' e inserir '{element_out}'...", end="")
                
                neighbor_solution = list(current_solution)
                neighbor_solution[i_sol_idx] = element_out
                neighbor_diversity = calculate_diversity(neighbor_solution, distance_matrix)
                
                # Se encontrou uma melhora, atualiza a solução e reinicia a busca
                if neighbor_diversity > current_diversity:
                    print(f" MELHORA.")
                    print(f"    >>> MOVIMENTO DE MELHORA ACEITO! Novo valor da F.O. ({neighbor_diversity:.2f}) > Valor atual ({current_diversity:.2f}).")
                    
                    current_solution = neighbor_solution
                    found_improvement = True
                    
                    print(f"    >>> Atualizando solução. A busca será reiniciada a partir deste novo ponto.")
                    break
                else:
                    print(" Movimento não melhora a solução.")
            if found_improvement:
                break
        
        iteration += 1
        # Se um ciclo foi feito sem melhorias, a solução é um ótimo local
        if not found_improvement:
            print("\nNenhum movimento de melhora encontrado na vizinhança. A solução é um ótimo local.")
            print("--- BUSCA LOCAL FINALIZADA ---\n")
            
            break
            
    return current_solution

# --- Bloco Principal de Execução ---
if __name__ == "__main__":
    # Parâmetros do problema
    N_ELEMENTS = 20
    M_SELECTION = 5

    # 1. Geração da Instância do Problema
    dist_matrix, m_size = generate_problem_instance(N_ELEMENTS, M_SELECTION)

    # 2. Construção da Solução Inicial
    initial_solution = greedy_constructive_heuristic(dist_matrix, m_size)
    initial_diversity = calculate_diversity(initial_solution, dist_matrix)
    print(f"Solução Inicial: {initial_solution}")
    print(f"Valor da Função Objetivo (Inicial): {initial_diversity:.2f}\n")

    # 3. Refinamento com Busca Local
    final_solution = local_search_first_improvement(initial_solution, dist_matrix, m_size)
    final_diversity = calculate_diversity(final_solution, dist_matrix)
    
    print("--- RESULTADOS FINAIS ---")
    print(f"Solução Inicial (Heurística Gulosa): {initial_solution} (Valor F.O.: {initial_diversity:.2f})")
    print(f"Solução Final (Após Busca Local):   {final_solution} (Valor F.O.: {final_diversity:.2f})")
    
    if final_diversity > initial_diversity:
        improvement = ((final_diversity - initial_diversity) / initial_diversity) * 100
        print(f"\nA busca local melhorou o valor da função objetivo em {improvement:.2f}%.")
    else:
        print("\nA busca local não encontrou uma solução superior à inicial (a solução inicial já era um ótimo local).")