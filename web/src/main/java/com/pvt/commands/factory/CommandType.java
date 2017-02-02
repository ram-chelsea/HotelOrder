package com.pvt.commands.factory;

import com.pvt.commands.Command;
import com.pvt.commands.impl.admin.*;
import com.pvt.commands.impl.client.*;
import com.pvt.commands.impl.user.*;

public enum CommandType {
    //user commands
    LOGIN, LOGOUT, REGISTRATION, GOTOREGISTRATION, GOTOLOGIN,

    //client commands
    LOOKCARDAMOUNT, CARDAMOUNT, ADDCREDITCARD, GOTOADDCREDITCARD, GOTOMAKEORDER, MAKEORDER, CLIENTORDERS, PAY, GOTOPAY, REQUEST, CANCEL, GOTOCLIENTSTARTPAGE,

    //admin commands
    CLIENTS, ADMINORDERS, ROOMS, GOTOCHANGEROOMPRICE, CHANGEROOMPRICE, GOTOADMINSTARTPAGE, CONFIRM, DENY, EXPIRE, GOTOADDNEWROOM, ADDNEWROOM;

    public Command getCurrentCommand() {
        switch (this) {
            case LOGIN:
                return new LoginUserCommand();

            case LOGOUT:
                return new LogoutUserCommand();

            case REGISTRATION:
                return new RegistrationCommand();

            case GOTOREGISTRATION:
                return new GoToRegistrationCommand();

            case GOTOLOGIN:
                return new GoToLoginCommand();

            case LOOKCARDAMOUNT:
                return new LookCardAmountCommand();

            case CARDAMOUNT:
                return new CardAmountCommand();

            case GOTOADDCREDITCARD:
                return new GoToAddCreditCardCommand();

            case ADDCREDITCARD:
                return new AddCreditCardCommand();

            case GOTOMAKEORDER:
                return new GoToMakeOrderCommand();

            case MAKEORDER:
                return new MakeOrderCommand();

            case CLIENTORDERS:
                return new ShowClientOrdersCommand();

            case GOTOPAY:
                return new GoToPayCommand();

            case PAY:
                return new PayCommand();

            case CANCEL:
                return new CancelCommand();

            case REQUEST:
                return new RequestCommand();

            case CONFIRM:
                return new ConfirmCommand();

            case DENY:
                return new DenyCommand();

            case EXPIRE:
                return new ExpireCommand();

            case GOTOCLIENTSTARTPAGE:
                return new GoToClientStartPageCommand();

            case ROOMS:
                return new ShowRoomsCommand();

            case GOTOCHANGEROOMPRICE:
                return new GoToChangeRoomPriceCommand();

            case CLIENTS:
                return new ShowClientsCommand();

            case ADMINORDERS:
                return new ShowAdminOrdersCommand();

            case CHANGEROOMPRICE:
                return new ChangeRoomPriceCommand();

            case GOTOADMINSTARTPAGE:
                return new GoAdminStartPageCommand();

            case GOTOADDNEWROOM:
                return new GoToAddNewRoomCommand();

            case ADDNEWROOM:
                return new AddNewRoomCommand();

            default:
                return new LoginUserCommand();

        }
    }

}
