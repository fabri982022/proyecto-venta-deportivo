package  com.peoyecto.venta.deportiva.deporte.DTO;

import lombok.Data;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.peoyecto.venta.deportiva.deporte.util.Rol;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long id_usuario;
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private String password;
    private String nombre_usuario;
    private Rol rol;
}