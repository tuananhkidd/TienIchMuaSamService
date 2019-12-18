package com.kidd.shopping.auth.service;

import com.kidd.shopping.auth.entity.LoginResult;
import com.kidd.shopping.auth.entity.model.JWTToken;
import com.kidd.shopping.auth.repository.UserRepository;
import com.kidd.shopping.base.response.ForbiddenResponse;
import com.kidd.shopping.base.response.NotFoundResponse;
import com.kidd.shopping.base.response.OkResponse;
import com.kidd.shopping.base.response.Response;
import com.kidd.shopping.base.tools.HttpPostRequestBuilder;
import com.kidd.shopping.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class LoginService {
    @Autowired
    private UserRepository userRespository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Response login(String username, String password, String role) {
        if(userRespository.getDataIDWithUsername(username) == null){
            return new NotFoundResponse(ResponseConstant.ErrorMessage.ACCOUNT_NOT_EXIST);
        }
        if (!userRespository.isAccountActivated(username, role)) {
            return new ForbiddenResponse(ResponseConstant.ErrorMessage.ACCOUNT_NOT_VERIFIED);
        }
        return login(username, password);
    }

    @Transactional(rollbackFor = Exception.class)
    public Response login(String username, String password) {
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("username", username);
        body.add("password", password);

        JWTToken token = new HttpPostRequestBuilder(restTemplate)
                .withUrl(ApplicationConstant.LOCAL_HOST + "/oauth/token")
                .setContentType(MediaType.APPLICATION_FORM_URLENCODED)
                .addToHeader(HeaderConstant.AUTHORIZATION, HeaderConstant.AUTHORIZATION_VALUE_PREFIX + Base64Utils.encode("trusted-app:secret"))
                .setFormDataBody(body)
                .execute(JWTToken.class);
        LoginResult loginResult = new LoginResult(userRespository.getDataIDWithUsername(username), token.getAccess_token(),
                token.getRefresh_token(),
                token.getExpires_in());
        return new OkResponse(loginResult);
    }

    //region facebook login
//    public Response facebookLogin(String facebookAccessToken, String customer) {
//        FacebookUserID facebookUserID = new HttpGetRequestBuilder(restTemplate)
//                .withParam(Constant.FIELDS, FacebookUser.ID)
//                .withParam(Constant.ACCESS_TOKEN, facebookAccessToken)
//                .withProtocol(HttpGetRequestBuilder.HTTPS)
//                .withUrl("graph.facebook.com/v2.11/me")
//                .execute(FacebookUserID.class);
//
//        String userID = facebookUserID.getId();
//        User user = userRespository.findByUsername(userID);
//        if (user == null) {
//            user = registerFacebookUser(userID, facebookAccessToken, customer);
//            user.setAccountType(Constant.FACEBOOK);
//        }
////        userAccessTokenRepository.updateUserFacebookAccessToken(userID, facebookAccessToken);
////        if (!user.getActived()) {
////            Student student = studentRepository.findOne(user.getId());
////            FacebookUserInfo facebookUserInfo = new FacebookUserInfo(student);
////            return new UnauthorizationResponse(ResponseConstant.ErrorMessage.ACCOUNT_NOT_VERIFIED, facebookUserInfo);
////        } else {
//        return login(userID, userID);
////        }
//    }

//    private User registerFacebookUser(String username, String accessToken, String role) {
//        FacebookUser facebookUser = new HttpGetRequestBuilder(restTemplate)
//                .withParam(Constant.FIELDS, FacebookUser.ID,
//                        FacebookUser.EMAIL,
//                        FacebookUser.BIRTHDAY,
//                        FacebookUser.GENDER,
//                        FacebookUser.FULL_NAME,
//                        FacebookUser.FIRST_NAME,
//                        FacebookUser.LAST_NAME,
//                        FacebookUser.COVER)
//                .withParam(Constant.ACCESS_TOKEN, accessToken)
//                .withProtocol(HttpGetRequestBuilder.HTTPS)
//                .withUrl("graph.facebook.com/v2.11/me")
//                .execute(FacebookUser.class);
//
//        FacebookAvatar facebookAvatar = new HttpGetRequestBuilder(restTemplate)
//                .withProtocol(HttpGetRequestBuilder.HTTPS)
//                .withParam(FacebookAvatar.TYPE, FacebookAvatar.LARGE)
//                .withParam(FacebookAvatar.REDIRECT, false)
//                .withParam(Constant.ACCESS_TOKEN, accessToken)
//                .withUrl("graph.facebook.com/v2.11/me/picture")
//                .execute(FacebookAvatar.class);
//
//        User user = new User();
//        user.setAccountType(Constant.FACEBOOK);
//        user.setUsername(username);
//        user.setPassword(bCryptPasswordEncoder.encode(username));
//        user.setRole(role);
//        user.setActived(true);
//        user = userRespository.save(user);
//
//        switch (role) {
//            case Constant.STUDENT: {
//                Student student = new Student();
//                student.setUser(user);
//                student.setLastName(facebookUser.getLast_name());
//                student.setFirstName(facebookUser.getFirst_name());
//                student.setBirthday(facebookUser.getBirthday());
//                student.setEmail(facebookUser.getEmail());
//                student.setGender(Gender.valueOf(DataValidator.getGender(facebookUser.getGender())));
//                Region region = regionRepository.findOne(Constant.DEFAULT_REGION);
//                student.setRegion(region);
//                if (facebookAvatar != null) {
//                    student.setAvatarUrl(facebookAvatar.getData().getUrl());
//                }
//                FacebookCover facebookCover = facebookUser.getCover();
//                if (facebookCover != null) {
//                    student.setCoverUrl(facebookCover.getSource());
//                }
//                studentRepository.save(student);
//            }
//            break;
//            default: {
//                break;
//            }
//        }
//        return user;
//    }
    //endregion
}
