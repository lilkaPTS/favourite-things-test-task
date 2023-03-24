package com.liluka.service.impl;

import com.liluka.config.jwt.JWTProvider;
import com.liluka.dto.FavouriteThingsEntryDTO;
import com.liluka.model.FavouriteThingsEntry;
import com.liluka.model.User;
import com.liluka.repository.FavouriteThingsEntryRepository;
import com.liluka.repository.UserRepository;
import com.liluka.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FavouriteThingsEntryRepository favouriteThingsEntryRepository;
    private final JWTProvider jwtProvider;
    private final ModelMapper modelMapper;

    @Override
    public FavouriteThingsEntry createFavouriteThingsEntry(FavouriteThingsEntryDTO dto, HttpServletRequest request) {
        return createFavouriteThingsEntry(dto, jwtProvider.getUsername(request.getHeader("Authorization")));
    }

    @Override
    public FavouriteThingsEntry createFavouriteThingsEntry(FavouriteThingsEntryDTO dto, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format("Пользователь %s не найден ", email)));
        Optional<FavouriteThingsEntry> favouriteThingsEntryOptional = favouriteThingsEntryRepository.findByUser(user);

        FavouriteThingsEntry favouriteThingsEntry;

        if(favouriteThingsEntryOptional.isPresent()) {
            favouriteThingsEntry = favouriteThingsEntryOptional.get();
            favouriteThingsEntry.setDish(dto.getDish());
            favouriteThingsEntry.setColor(dto.getColor());
            favouriteThingsEntry.setSong(dto.getSong());
            favouriteThingsEntry.setNumber(dto.getNumber());
            favouriteThingsEntry.setDate(dto.getDate());
        } else {
            favouriteThingsEntry = modelMapper.map(dto, FavouriteThingsEntry.class);
            favouriteThingsEntry.setUser(user);
        }
        return favouriteThingsEntryRepository.save(favouriteThingsEntry);
    }
}
