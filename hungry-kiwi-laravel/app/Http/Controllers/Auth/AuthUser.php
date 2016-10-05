<?php
namespace App\Http\Controllers\Auth;

use Illuminate\Foundation\Validation\ValidatesRequests;
use Illuminate\Http\Request;
use App\User;
use App\LoginToken;
use GuzzleHttp\Client;

define('FACEBOOK', 0);
define('GOOGLE', 1);

class AuthUser {
	
	use ValidatesRequests;
	protected $req;

	public function __construct(Request $req) {
		$this -> req = $req;
	}


	public function start() {

		if($this -> verifyToken()) {
			$user = User::login($this -> req);
			$this -> activeToken($user);
			$json = [
				'token' => $this->req->token,
				'user' => json_decode($user -> toJson(), true),
			];
			return json_encode($json);
			// return 'abc';
		
		} else {
			return 'TOKEN INVALID';
		}
	}

	public function verifyToken() {
		$provider = $this -> req -> provider;
		if($provider == FACEBOOK) {
			return $this -> verifyFacebook();
		} else if($provider == GOOGLE) {
			return $this -> verifyGoogle();
		}
	}

	public function verifyFacebook() {
		$base_uri = 'https://graph.facebook.com/me';
    	$token = $this -> req -> token;
    	$client = new Client();
    	$result = $client->request('GET', $base_uri, [
    		'verify' => false,
        	'query' => [
            	'access_token' => $token,
            ]]);
    	$body =  $result -> getBody();
    	if(count($body) > 0) return true;
    	else return false;
	}

	public function verifyGoogle() {
		$base_uri = 'https://www.googleapis.com/oauth2/v3/tokeninfo';
    	$token = $this -> req -> token;
    	$client = new Client();
    	$result = $client->request('GET', $base_uri, [
    		'verify' => false,
        	'query' => [
            	'id_token' => $token,
            ]]);
    	
    	$body =  $result -> getBody();
    	if(count($body) > 0) return true;
    	else return false;
	}

	public function activeToken(User $user) {
		$token = $user -> hasOne('App\LoginToken');

		if(count($token) > 0 ) {
			$token -> delete();
		}
		
		$info = [
			'user_id' => $user -> id,
			'token' => $this -> req -> token,
		];

		LoginToken::create($info);
	}
}