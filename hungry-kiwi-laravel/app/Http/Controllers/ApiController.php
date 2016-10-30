<?php

namespace App\Http\Controllers;

use App\LoginToken;
use App\User;
use Illuminate\Http\Request;

use App\Http\Requests;

class ApiController extends Controller {

    public function successArray(array $data) {
        return response() -> json($data, 200);
    }
    public function successText($data) {
        return response() -> json(['message' => $data], 200);
    }




    public function notFound() {
        return $this -> error('Page Not Found', 422);
    }

    public function forbidden() {
        $this -> error("Access is fobidden", 401);
    }

    public function error($message, $status_code) {
        return response() -> json(['error' => ['message' => $message, 'status_code' => $status_code,]], $status_code);
    }

    public function getUserId(Request $request) {
        return User::getUserIdbyToken($request -> header('Authorization'));
    }

    public function cuAuthenticate(Request $request) {
        $token = $request -> header('Authorization');
        $userId = LoginToken::where('token', $token) -> first() -> user_id;
        return $request -> get('user_id') != $userId;
    }
}
