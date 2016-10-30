<?php

namespace App\Http\Middleware;
use App\LoginToken;
use App\User;
use Request;
use Closure;

class Authentication
{
    /**
     * Handle an incoming request.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \Closure  $next
     * @return mixed
     */
    public function handle($request, Closure $next)
    {
        $token =  $request -> header('Authorization');
        $login_token = LoginToken::where('token', $token) -> first();
        if(count($login_token) == 0) {
            return response() -> json([
                'error' => [
                    'message' => 'INVALID_TOKEN',
                    'status_code' => 401
                ]
            ], 401);
        }
        return $next($request);
    }
}
