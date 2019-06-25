package fraktikant.tflcstefan.hybrit.app.service.impl;

import fraktikant.tflcstefan.hybrit.app.entity.ConfirmationToken;
import fraktikant.tflcstefan.hybrit.app.entity.User;
import fraktikant.tflcstefan.hybrit.app.repository.ConfirmationTokenRepository;
import fraktikant.tflcstefan.hybrit.app.repository.UserRepository;
import fraktikant.tflcstefan.hybrit.app.service.ConfirmationTokenService;
import fraktikant.tflcstefan.hybrit.app.web.dto.UserDTO;
import fraktikant.tflcstefan.hybrit.app.web.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmTokenRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public ConfirmationToken generateToken(User user) {
        ConfirmationToken ct = new ConfirmationToken(user);
        return confirmTokenRepository.save(ct);
    }

    @Override
    public Boolean confirmUser(String confirmationToken) {

        ConfirmationToken ct = confirmTokenRepository.findByConfirmationToken(confirmationToken);

        UserDTO user = userMapper.toDTO(userRepository.findByUsername(ct.getUser().getUsername()));
        user.setIsEnabled(true);
        userRepository.save(userMapper.toEntity(user));

        confirmTokenRepository.deleteById(ct.getId());

        return true;
    }
}
