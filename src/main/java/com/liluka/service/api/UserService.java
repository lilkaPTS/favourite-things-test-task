package com.liluka.service.api;

import com.liluka.dto.FavouriteThingsEntryDTO;
import com.liluka.model.FavouriteThingsEntry;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    FavouriteThingsEntry createFavouriteThingsEntry(FavouriteThingsEntryDTO dto, HttpServletRequest request);
    FavouriteThingsEntry createFavouriteThingsEntry(FavouriteThingsEntryDTO dto, String email);
}
