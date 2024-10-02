package br.edu.ifpb.pweb2.estagion.ui;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class NavPage {
    private int currentPage;
    private long totalItems;
    private int totalPages;
    private int pageSize;

}
