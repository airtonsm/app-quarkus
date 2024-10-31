package tech.buildrum.service;

import jakarta.enterprise.context.ApplicationScoped;
import tech.buildrum.entity.UserEntity;
import tech.buildrum.exception.UserNotFoundException;

import java.util.List;
import java.util.UUID;

@ApplicationScoped

public class UserService {
    public UserEntity createUser(UserEntity userEntity) {
        UserEntity.persist(userEntity); // colocar return nessa linha, 
        return userEntity;
    }

    public List<UserEntity> findAll(Integer page, Integer pageSize) {
        return UserEntity.findAll()
                .page(page, pageSize)
                .list();
    }

    public UserEntity findById(UUID userId) {
       return (UserEntity) UserEntity.findByIdOptional(userId)
               .orElseThrow(UserNotFoundException::new);
    }

    public UserEntity updateUser(UUID userId, UserEntity userEntity) {
         var user = findById(userId);

         user.username = userEntity.username; //usar encapsulamento, getUserName(userEntity.username)

         UserEntity.persist(user); // colcar return nessa linha

        return user;
    }

    public void deleteById(UUID userId) {
        var user = findById(userId); //vc está buscando o usuário com o id, e depois usando o mesmo id do usuário que vc buscou? User apenas o userId no delete
                                    // se vc quis garanti que o usupario existia, essa validação está na forma errada
        UserEntity.deleteById(user.userId);
    }
}
