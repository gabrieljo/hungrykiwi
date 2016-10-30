<?php

namespace App\Http\Controllers\Auth;

use Illuminate\Http\Request;
use App\Http\Controllers\Auth\AuthUser;
use App\Http\Requests;
use App\Http\Controllers\Controller;


class AuthController extends Controller{
 

	protected $auth;
	public function __construct(AuthUser $auth) {
		$this -> auth = $auth;
	}

    public function postLogin() {
    	return $this -> auth -> start();
    }
}
