package com.example.team_7_case_8_product_management.service;

public class DefaultClientService {
//    private final ClientRepository userRepository;
//
//    @Override
//    public void register(String clientId, String clientSecret) {
//        if(userRepository.findById(clientId).isPresent())
//            throw new RegistrationException(
//                    "Client with id: " + clientId + " already registered");
//
//        String hash = BCrypt.hashpw(clientSecret, BCrypt.gensalt());
//        userRepository.save(new ClientEntity(clientId, hash));
//    }
//
//    @Override
//    public void checkCredentials(String clientId, String clientSecret) {
//        Optional<ClientEntity> optionalUserEntity = userRepository
//                .findById(clientId);
//        if (optionalUserEntity.isEmpty())
//            throw new LoginException(
//                    "Client with id: " + clientId + " not found");
//
//        ClientEntity clientEntity = optionalUserEntity.get();
//
//        if (!BCrypt.checkpw(clientSecret, clientEntity.getHash()))
//            throw new LoginException("Secret is incorrect");
//    }
}

